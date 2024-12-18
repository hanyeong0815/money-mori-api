package com.side.moneymoriapi.aop.member;

import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;
import com.side.moneymoriapi.exception.member.MemberErrorCode;
import com.side.moneymoriapi.mapper.member.MemberQueryMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.side.moneymoriapi.utils.exception.Preconditions.validate;

@Aspect
@Component
@RequiredArgsConstructor
public class MemberAspect {
    private final MemberQueryMapper memberQueryMapper;

    @Before(value = "@annotation(HasMember) && args(dto)")
    public void hasMemberByUsername(CreateMemberRequestDto dto) {
        // if exists username Throw MemberException
        validate(
                0 >= memberQueryMapper.countMemberByUsername(dto.username()),
                MemberErrorCode.USERNAME_ALREADY_USED
        );
    }
}
