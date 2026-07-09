package com.wise.user_service.user.domain;

public record ChangePasswordCommand(
        Long id,
        String oldPassword,
        String newPassword
) {
}
