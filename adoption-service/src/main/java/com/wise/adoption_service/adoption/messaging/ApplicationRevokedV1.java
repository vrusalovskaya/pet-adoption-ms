package com.wise.adoption_service.adoption.messaging;

import java.time.Instant;
import java.util.UUID;

public record ApplicationRevokedV1(
        UUID eventId,
        Long applicationId,
        Long animalId,
        Instant occurredAt
) {
}
