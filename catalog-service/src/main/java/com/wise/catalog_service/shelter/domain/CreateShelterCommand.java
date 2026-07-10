package com.wise.catalog_service.shelter.domain;

public record CreateShelterCommand(
        String name,
        String city,
        String address,
        String contactEmail,
        String contactPhone,
        String description
) {
}
