package com.wise.catalog_service.animal.domain;

import com.wise.catalog_service.animal.common.AnimalStatus;
import com.wise.catalog_service.animal.common.Gender;
import com.wise.catalog_service.animal.common.Species;

import java.time.Instant;

public record Animal(
        Long id,
        Long shelterId,
        String name,
        Species species,
        String breed,
        Integer birthYear,
        Gender gender,
        String description,
        AnimalStatus status,
        PhotoMetadataModel photoMetadata,
        Instant createdAt
) {
}
