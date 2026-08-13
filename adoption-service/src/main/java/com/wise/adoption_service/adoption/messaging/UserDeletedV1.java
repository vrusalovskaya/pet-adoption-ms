package com.wise.adoption_service.adoption.messaging;

import java.time.Instant;
import java.util.UUID;

public record UserDeletedV1(
        UUID eventId,
        Long userId,
        Instant occurredAt
) {
}
