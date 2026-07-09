package com.wise.user_service.security.domain;

public record RegisterCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {
}
