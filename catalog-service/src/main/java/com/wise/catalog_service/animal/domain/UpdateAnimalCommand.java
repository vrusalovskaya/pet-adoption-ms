package com.wise.catalog_service.animal.domain;

import com.wise.catalog_service.animal.common.Gender;
import com.wise.catalog_service.animal.common.Species;

public record UpdateAnimalCommand(
        Long id,
        Long shelterId,
        String name,
        Species species,
        String breed,
        Integer birthYear,
        Gender gender,
        String description
) {
}
