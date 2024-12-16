package com.side.moneymoriapi.domain.member;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}
