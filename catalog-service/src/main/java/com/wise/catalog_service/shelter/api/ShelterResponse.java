package com.wise.catalog_service.shelter.api;

import java.time.Instant;

public record ShelterResponse(
        Long id,
        String name,
        String city,
        String address,
        String contactEmail,
        String contactPhone,
        String description,
        boolean verified,
        Instant createdAt
) {
}
