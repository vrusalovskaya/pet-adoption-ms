package com.wise.adoption_service.adoption.service;

import com.wise.adoption_service.adoption.common.OutboxStatus;
import com.wise.adoption_service.adoption.persistence.ApplicationRevokedOutboxEntity;
import com.wise.adoption_service.adoption.persistence.ApplicationRevokedOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationRevokedOutboxService {

    private final ApplicationRevokedOutboxRepository repository;
    private final Clock clock;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ApplicationRevokedOutboxEntity> claimPendingEvents(int limit) {
        List<ApplicationRevokedOutboxEntity> events = repository.findPending(PageRequest.of(0, limit));
        Instant now = clock.instant();
        events.forEach(e -> {
            e.setStatus(OutboxStatus.PROCESSING);
            e.setProcessingStartedAt(now);
        });
        return events;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsPublished(UUID id) {
        repository.markAsPublished(id, clock.instant());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int releaseStuckEvents(Instant threshold) {
        return repository.releaseStuckEvents(threshold);
    }
}