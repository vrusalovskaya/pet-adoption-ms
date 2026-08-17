package com.wise.adoption_service.adoption.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ApplicationRevokedOutboxRepository extends JpaRepository<ApplicationRevokedOutboxEntity, UUID> {

    @Query("""
            SELECT e FROM ApplicationRevokedOutboxEntity e
            WHERE e.status = 'PENDING' ORDER BY e.occurredAt
            """)
    List<ApplicationRevokedOutboxEntity> findPending(Pageable pageable);

    @Modifying
    @Query("UPDATE ApplicationRevokedOutboxEntity e SET e.status = 'PUBLISHED', e.publishedAt = :now WHERE e.eventId = :id")
    void markAsPublished(@Param("id") UUID id, @Param("now") Instant now);

    @Modifying
    @Query("""
            UPDATE ApplicationRevokedOutboxEntity e
            SET e.status = 'PENDING', e.processingStartedAt = null
            WHERE e.status = 'PROCESSING' AND e.processingStartedAt < :threshold
            """)
    int releaseStuckEvents(@Param("threshold") Instant threshold);
}
