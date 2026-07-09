package com.wise.user_service.user.domain;

import com.wise.user_service.user.common.Role;

public record CreateUserCommand(
        String email,
        String rawPassword,
        String firstName,
        String lastName,
        String phone,
        Role role
) {
}
