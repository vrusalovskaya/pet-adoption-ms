package com.wise.catalog_service.animal.api;

import com.wise.catalog_service.animal.common.AnimalStatus;
import com.wise.catalog_service.animal.common.Gender;
import com.wise.catalog_service.animal.common.Species;

import java.net.URI;
import java.time.Instant;

public record AnimalResponse(
        Long id,
        Long shelterId,
        String name,
        Species species,
        String breed,
        Integer birthYear,
        Gender gender,
        String description,
        AnimalStatus status,
        URI photoUrl,
        Instant createdAt
) {
}
