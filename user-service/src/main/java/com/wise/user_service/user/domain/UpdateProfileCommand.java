package com.wise.user_service.user.domain;

public record UpdateProfileCommand(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
