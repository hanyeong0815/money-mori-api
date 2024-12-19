package com.side.moneymoriapi.redis.repository;

import com.side.moneymoriapi.redis.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
