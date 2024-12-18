package com.side.moneymoriapi.domain.member;

import com.side.moneymoriapi.domain.type.RoleType;
import lombok.*;

import java.time.Instant;
import java.util.List;
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
    private List<RoleType> roles;
}
