package com.wise.user_service.user.service;

import com.wise.user_service.user.persistence.UserDeletedOutboxEntity;
import com.wise.user_service.user.persistence.UserDeletedOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDeletedOutboxService {

    private final UserDeletedOutboxRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<UserDeletedOutboxEntity> claimEvents(int limit) {
        return repository.claimPendingEvents(limit);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsPublished(UUID id) {
        repository.markAsPublished(id, Instant.now());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int releaseStuckEvents(Instant threshold) {
        return repository.releaseStuckEvents(threshold);
    }
}
