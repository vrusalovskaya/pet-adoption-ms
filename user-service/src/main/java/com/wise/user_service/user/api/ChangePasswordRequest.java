package com.wise.user_service.user.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @Size(min = 8, max = 100)
        @NotBlank
        String oldPassword,

        @Size(min = 8, max = 100)
        @NotBlank
        String newPassword
) {
}
