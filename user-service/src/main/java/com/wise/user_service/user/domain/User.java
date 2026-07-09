package com.wise.user_service.user.domain;

import com.wise.user_service.user.common.Role;

import java.time.Instant;

public record User(
        Long id,
        String email,
        String passwordHash,
        String firstName,
        String lastName,
        String phone,
        Role role,
        Instant createdAt
) {
}
