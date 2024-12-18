package com.side.moneymoriapi.usecase.member;

import com.side.moneymoriapi.domain.member.Member;
import com.side.moneymoriapi.dto.member.CreateMemberDto.CreateMemberRequestDto;

public interface CreateMemberUseCase {
    Member createMember(CreateMemberRequestDto dto);
}
