package com.wise.user_service.security.domain;

public record RefreshTokenRotationResult(
        String rawToken,
        Long userId
) {
}
