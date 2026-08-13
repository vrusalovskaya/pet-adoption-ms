package com.wise.user_service.user.service;

import com.wise.user_service.user.mapper.UserDeletedEventMapper;
import com.wise.user_service.user.persistence.UserDeletedOutboxEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void publishPending() {
        List<UserDeletedOutboxEntity> events = outboxService.claimEvents(100);
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
        Instant threshold = Instant.now().minus(Duration.ofMinutes(5));
        int recoveredCount = outboxService.releaseStuckEvents(threshold);
        if (recoveredCount > 0) {
            log.warn("Recovered {} stuck outbox events", recoveredCount);
        }
    }
}
