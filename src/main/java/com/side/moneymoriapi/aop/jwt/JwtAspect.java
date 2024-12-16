package com.side.moneymoriapi.aop.jwt;

import com.side.moneymoriapi.exception.jwt.JwtErrorCode;
import com.side.moneymoriapi.utils.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.side.moneymoriapi.utils.exception.Preconditions.validate;

@Component
@Aspect
@RequiredArgsConstructor
public class JwtAspect {
    private final JwtProvider jwtProvider;
    private final HttpServletRequest httpRequest;

    public void validateJwtToken(JoinPoint joinPoint, JwtSecured jwtSecured) {
        String authHeader = httpRequest.getHeader("Authorization");

        validate(
                authHeader != null || authHeader.startsWith("Bearer "),
                JwtErrorCode.INVALID_JWT_TOKEN
        );

        String token = authHeader.substring(7);

        Claims claims = jwtProvider.validateToken(token);

        if (jwtSecured.roles().length > 0) {
            String userRole = claims.get("role", String.class);
            boolean hasRole = false;

            for (String role : jwtSecured.roles()) {
                if (role.equals(userRole)) {
                    hasRole = true;
                    break;
                }
            }

            validate(
                    hasRole,
                    JwtErrorCode.INVALID_JWT_TOKEN
            );
        }
    }
}
