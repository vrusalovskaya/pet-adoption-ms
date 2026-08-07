package com.wise.adoption_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(JwtProperties properties) {
        if (properties.getSecret().length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters long");
        }

        this.key = Keys.hmacShaKeyFor(
                properties.getSecret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public Optional<Authentication> getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Long userId = claims.get("userId", Long.class);
            String email = claims.getSubject();
            Role role = Role.valueOf(claims.get("role", String.class));

            SecurityUser principal = new SecurityUser(userId, email, null, null, null, role);

            return Optional.of(new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    principal.getAuthorities()
            ));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
