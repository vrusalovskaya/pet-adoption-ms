package com.wise.user_service.user.persistence;

import com.wise.user_service.user.common.OutboxStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "outbox", name = "user_deleted_outbox")
@Getter
@NoArgsConstructor
public class UserDeletedOutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private OutboxStatus status;

    @Column(nullable = false)
    private Instant occurredAt;

    private Instant lockedAt;

    private Instant publishedAt;

    public UserDeletedOutboxEntity(Long userId, Instant occurredAt) {
        this.userId = userId;
        this.status = OutboxStatus.PENDING;
        this.occurredAt = occurredAt;
    }
}