package com.wise.user_service.user.api;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone
) {
}
