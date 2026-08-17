package com.wise.catalog_service.animal.messaging;

import java.time.Instant;
import java.util.UUID;

public record ApplicationRevokedV1(
        UUID eventId,
        Long applicationId,
        Long animalId,
        Instant occurredAt
) {
}
