package com.side.moneymoriapi.usecase.member;

import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberRequestDto;
import com.side.moneymoriapi.dto.member.LoginMemberDto.LoginMemberResponseDto;

public interface LoginMemberUseCase {
    LoginMemberResponseDto loginMember(LoginMemberRequestDto dto);
}
