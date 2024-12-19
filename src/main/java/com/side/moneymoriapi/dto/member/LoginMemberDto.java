package com.side.moneymoriapi.dto.member;

import lombok.Builder;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record LoginMemberDto() {
    public record LoginMemberRequestDto(
            @NotBlank
            String username,
            @NotBlank
            String password
    ) {}

    @Builder
    public record LoginMemberResponseDto(
            String username,
            JwtTokenPair tokenPair
    ) {}

    @Builder
    public record JwtTokenPair(
            String accessToken,
            String refreshToken
    ) {}
}
