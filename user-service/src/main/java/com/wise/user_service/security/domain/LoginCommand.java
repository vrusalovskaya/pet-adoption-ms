package com.wise.user_service.security.domain;

public record LoginCommand(
        String email,
        String password
) {
}
