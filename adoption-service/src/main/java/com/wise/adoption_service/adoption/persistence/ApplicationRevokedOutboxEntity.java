package com.wise.adoption_service.adoption.persistence;

import com.wise.adoption_service.adoption.common.OutboxStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "outbox", name = "application_revoked_outbox")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationRevokedOutboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private Long applicationId;

    @Column(nullable = false)
    private Long animalId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private OutboxStatus status;

    @Column(nullable = false)
    private Instant occurredAt;

    private Instant processingStartedAt;

    private Instant publishedAt;

    public ApplicationRevokedOutboxEntity(Long applicationId, Long animalId, Instant occurredAt) {
        this.applicationId = applicationId;
        this.animalId = animalId;
        this.status = OutboxStatus.PENDING;
        this.occurredAt = occurredAt;
    }
}