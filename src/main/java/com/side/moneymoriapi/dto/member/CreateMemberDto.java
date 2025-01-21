package com.side.moneymoriapi.dto.member;

import jakarta.validation.constraints.NotBlank;

public record CreateMemberDto() {
    public record CreateMemberRequestDto(
            @NotBlank
            String username,
            @NotBlank
            String password,
            @NotBlank
            String nickname,
            String email
    ) {}
}
