package com.wise.user_service.security.api;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
