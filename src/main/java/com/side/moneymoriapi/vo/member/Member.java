package com.side.moneymoriapi.vo.member;

import com.side.moneymoriapi.vo.type.RoleType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}
