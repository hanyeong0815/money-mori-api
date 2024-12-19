package com.side.moneymoriapi.service;

import com.side.moneymoriapi.aop.member.HasMember;
import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.domain.type.RoleType;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.JwtTokenPair;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberResponseDto;
import com.side.moneymoriapi.exception.member.MemberErrorCode;
import com.side.moneymoriapi.mapper.member.MemberCommendMapper;
import com.side.moneymoriapi.mapper.member.MemberQueryMapper;
import com.side.moneymoriapi.projection.member.MemberProjection.MemberUsernamePasswordProjection;
import com.side.moneymoriapi.redis.domain.RefreshToken;
import com.side.moneymoriapi.redis.repository.RefreshTokenRepository;
import com.side.moneymoriapi.usecase.member.CreateMemberUseCase;
import com.side.moneymoriapi.usecase.member.LoginMemberUseCase;
import com.side.moneymoriapi.utils.jwt.JwtProvider;
import com.side.moneymoriapi.utils.random.StrongStringRandom;
import com.side.moneymoriapi.utils.time.ServerTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.side.moneymoriapi.utils.exception.Preconditions.validate;

@Service
@RequiredArgsConstructor
public class MemberService implements CreateMemberUseCase, LoginMemberUseCase {
    private final MemberCommendMapper commendMapper;
    private final MemberQueryMapper queryMapper;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder pwEncoder;

    private final JwtProvider jwtProvider;
    private final MemberQueryMapper memberQueryMapper;

    private final StrongStringRandom random;
    private final ServerTime serverTime;

    @HasMember
    @Override
    public Member createMember(CreateMemberRequestDto dto) {
        // member build
        Member newMember = Member.builder()
                .username(dto.username())
                .password(pwEncoder.encode(dto.password()))
                .email(dto.email())
                .roles(List.of(RoleType.USER))
                .build();

        // member save
        commendMapper.createMember(newMember);
        // member roles save
        commendMapper.insertRoles(newMember.getId(), newMember.getRoles());

        return newMember;
    }

    @Override
    public LoginMemberResponseDto loginMember(LoginMemberRequestDto dto) {
        // find member and is empty throw exception
        MemberUsernamePasswordProjection projection = queryMapper.findByUsernameAndPassword(dto.username())
                .orElseThrow(
                        MemberErrorCode.INVALID_USERNAME_OR_PASSWORD::defaultException
                );

        // matching password if not matched throw exception
        validate(
                pwEncoder.matches(dto.password(), projection.password()),
                MemberErrorCode.INVALID_USERNAME_OR_PASSWORD
        );

        // find member's roles and convert List<String> to String
        String roles = String.join(",", memberQueryMapper.findRolesByMemberId(projection.id()));

        // create accessToken and RefreshToken and create dto for response
        JwtTokenPair tokenPair = JwtTokenPair.builder()
                .accessToken(jwtProvider.createToken(projection.username(), roles))
                .refreshToken(createRefreshToken(projection.username()))
                .build();

        return LoginMemberResponseDto.builder()
                .username(projection.username())
                .tokenPair(tokenPair)
                .build();
    }

    private String createRefreshToken(String subject) {
        Instant now = serverTime.nowInstant();
        String refreshToken = random.nextString(); // create refreshToken

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .subject(subject)
                .refreshToken(refreshToken)
                .createdAt(now)
                .ttl(1L)
                .build();

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshTokenEntity); // saving to redis
        return savedRefreshToken.getRefreshToken();
    }
}
