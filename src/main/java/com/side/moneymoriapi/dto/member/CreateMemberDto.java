package com.side.moneymoriapi.dto.member;

public record CreateMemberDto() {
    public record CreateMemberRequestDto(
            String username,
            String password,
            String email
    ) {}
}
