package com.wise.user_service.security.domain;

public record LogoutCommand(
        String refreshToken
) {
}
