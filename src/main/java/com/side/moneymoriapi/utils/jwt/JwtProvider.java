package com.side.moneymoriapi.utils.jwt;

import com.side.moneymoriapi.exception.jwt.JwtErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    // token expire time (1hour)
    private static final Long validityInMilliseconds = 86_400_000L;

    // create JWT
    public String createToken(String username, String roles) {
        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("auth", roles)
                .setExpiration(accessTokenExpiresIn)
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw JwtErrorCode.INVALID_JWT_TOKEN.defaultException();
        } catch (ExpiredJwtException e) {
            throw JwtErrorCode.EXPIRED_JWT_TOKEN.defaultException();
        } catch (UnsupportedJwtException e) {
            throw JwtErrorCode.UNSUPPORTED_JWT_TOKEN.defaultException();
        } catch (IllegalArgumentException e) {
            throw JwtErrorCode.CLAIMS_IS_EMPTY.defaultException();
        }
    }
}
