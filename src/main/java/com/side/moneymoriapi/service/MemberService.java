package com.side.moneymoriapi.service;

import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.mapper.member.MemberCommendMapper;
import com.side.moneymoriapi.usecase.member.CreateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements CreateMemberUseCase {
    private final MemberCommendMapper commendMapper;

    private final PasswordEncoder pwEncoder;

    @Override
    public Member createMember(CreateMemberRequestDto dto) {
        Member newMember = Member.builder()
                .username(dto.username())
                .password(pwEncoder.encode(dto.password()))
                .email(dto.email())
                .build();

        commendMapper.createMember(newMember);

        return newMember;
    }
}
