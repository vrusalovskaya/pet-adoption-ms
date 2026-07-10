package com.wise.catalog_service.animal.api;

import com.wise.catalog_service.animal.common.AnimalStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAnimalStatusRequest(
        @NotNull(message = "Status is mandatory")
        AnimalStatus status
) {
}
