package com.wise.adoption_service.adoption.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequest(
        @NotNull
        Long animalId,
        @NotBlank
        String message
) {
}
