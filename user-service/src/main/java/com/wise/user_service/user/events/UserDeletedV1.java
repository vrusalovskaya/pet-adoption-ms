package com.wise.user_service.user.events;

import java.time.Instant;
import java.util.UUID;

public record UserDeletedV1(
        UUID eventId,
        Long userId,
        Instant occurredAt
) {
}
