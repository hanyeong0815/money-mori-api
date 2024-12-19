package com.side.moneymoriapi.projection.member;

import java.util.UUID;

public record MemberProjection() {
    public record MemberUsernamePasswordProjection (
            UUID id,
            String username,
            String password
    ) {}
}
