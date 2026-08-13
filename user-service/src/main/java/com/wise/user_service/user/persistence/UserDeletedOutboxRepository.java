package com.wise.user_service.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserDeletedOutboxRepository extends JpaRepository<UserDeletedOutboxEntity, UUID> {

    @Query(value = """
            UPDATE outbox.user_deleted_outbox
            SET status = 'PROCESSING',
                locked_at = NOW()
            WHERE event_id IN (
                SELECT event_id
                FROM outbox.user_deleted_outbox
                WHERE status = 'PENDING'
                ORDER BY occurred_at
                FOR UPDATE SKIP LOCKED
                LIMIT :limit
            )
            RETURNING *
            """, nativeQuery = true)
    List<UserDeletedOutboxEntity> claimPendingEvents(@Param("limit") int limit);

    @Modifying
    @Query("UPDATE UserDeletedOutboxEntity e SET e.status = 'PUBLISHED', e.publishedAt = :now WHERE e.id = :id")
    void markAsPublished(@Param("id") UUID id, @Param("now") Instant now);

    @Modifying
    @Query("""
            UPDATE UserDeletedOutboxEntity e
            SET e.status = 'PENDING', e.lockedAt = null
            WHERE e.status = 'PROCESSING' AND e.lockedAt < :threshold
            """)
    int releaseStuckEvents(@Param("threshold") Instant threshold);
}
