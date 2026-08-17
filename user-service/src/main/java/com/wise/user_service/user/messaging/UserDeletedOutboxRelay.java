package com.wise.user_service.user.messaging;

import com.wise.user_service.user.mapper.UserDeletedEventMapper;
import com.wise.user_service.user.persistence.UserDeletedOutboxEntity;
import com.wise.user_service.user.service.UserDeletedOutboxService;
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
public class UserDeletedOutboxRelay {

    private final UserDeletedKafkaPublisher publisher;
    private final UserDeletedOutboxService outboxService;
    private final UserDeletedEventMapper mapper;
    private final Clock clock;

    public void publishPending() {
        List<UserDeletedOutboxEntity> events = outboxService.claimPendingEvents(100);
        if (events.isEmpty()) {
            return;
        }

        for (UserDeletedOutboxEntity event : events) {
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
