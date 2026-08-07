package com.wise.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
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
            String rawRole = claims.get("role", String.class);

            List<GrantedAuthority> authorities = Collections.emptyList();
            if (rawRole != null) {
                String authorityName = rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole;
                authorities = List.of(new SimpleGrantedAuthority(authorityName));
            }

            return Optional.of(new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    authorities
            ));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
