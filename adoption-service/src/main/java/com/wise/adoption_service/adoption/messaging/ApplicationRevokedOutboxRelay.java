package com.wise.adoption_service.adoption.messaging;

import com.wise.adoption_service.adoption.mapper.ApplicationRevokedEventMapper;
import com.wise.adoption_service.adoption.persistence.ApplicationRevokedOutboxEntity;
import com.wise.adoption_service.adoption.service.ApplicationRevokedOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationRevokedOutboxRelay {
    private final ApplicationRevokedKafkaPublisher publisher;
    private final ApplicationRevokedOutboxService outboxService;
    private final ApplicationRevokedEventMapper mapper;
    private final Clock clock;

    public void publishPending() {
        List<ApplicationRevokedOutboxEntity> events = outboxService.claimPendingEvents(100);
        if (events.isEmpty()) {
            return;
        }

        for (ApplicationRevokedOutboxEntity event : events) {
            try {
                publisher.publish(mapper.toEvent(event));
                outboxService.markAsPublished(event.getEventId());
            } catch (Exception e) {
                log.error("Failed to publish outbox event with id: {}", event.getEventId(), e);
                break;
            }
        }
    }

    public void recoverStuckEvents() {
        Instant threshold = clock.instant().minus(Duration.ofMinutes(5));
        int recoveredCount = outboxService.releaseStuckEvents(threshold);
        if (recoveredCount > 0) {
            log.warn("Recovered {} stuck outbox events", recoveredCount);
        }
    }
}
