package com.wise.user_service.security.domain;

public record AuthenticationResult(
        String accessToken,
        String refreshToken
) {
}
