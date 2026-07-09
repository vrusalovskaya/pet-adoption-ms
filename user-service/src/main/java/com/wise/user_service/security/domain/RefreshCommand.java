package com.wise.user_service.security.domain;

public record RefreshCommand(
        String refreshToken
) {
}
