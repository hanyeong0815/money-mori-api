package com.side.moneymoriapi.service;

import com.side.moneymoriapi.aop.member.HasMember;
import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.domain.type.RoleType;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.mapper.member.MemberCommendMapper;
import com.side.moneymoriapi.usecase.member.CreateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements CreateMemberUseCase {
    private final MemberCommendMapper commendMapper;

    private final PasswordEncoder pwEncoder;

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
}
