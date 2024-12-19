package com.side.moneymoriapi.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Builder
@RedisHash(value = "refresh_token")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String refreshToken;
    @Indexed
    private String subject;
    private Instant createdAt;
    @TimeToLive(unit = TimeUnit.DAYS)
    private Long ttl;
}
