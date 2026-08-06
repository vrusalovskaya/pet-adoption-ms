package com.wise.adoption_service.support;

import com.wise.adoption_service.adoption.common.ApplicationStatus;
import com.wise.adoption_service.adoption.domain.Application;
import com.wise.adoption_service.adoption.persistence.ApplicationEntity;
import com.wise.adoption_service.security.Role;
import com.wise.adoption_service.security.SecurityUser;

import java.time.Instant;

public final class TestFixtures {

    public static final Instant NOW = Instant.parse("2026-01-01T12:00:00.00Z");

    private TestFixtures() {
    }

    public static SecurityUser securityUser(Long id, String email, Role role) {
        return new SecurityUser(id, email, "hashed-secret", "Jane", "Doe", role);
    }

    public static ApplicationEntity createApplicationEntity(Long animalId, Long applicantId, String message) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setAnimalId(animalId);
        entity.setApplicantId(applicantId);
        entity.setMessage(message);
        return entity;
    }

    public static ApplicationEntity applicationEntity(Long id, Long animalId, Long applicantId,
                                                      ApplicationStatus status) {
        ApplicationEntity entity = new ApplicationEntity();
        entity.setId(id);
        entity.setAnimalId(animalId);
        entity.setApplicantId(applicantId);
        entity.setMessage("Please let me adopt");
        entity.setStatus(status);
        entity.setCreatedAt(NOW);
        entity.setUpdatedAt(NOW);
        return entity;
    }

    public static Application application(Long id, Long animalId, Long applicantId,
                                          ApplicationStatus status) {
        return new Application(id, animalId, applicantId, "Please let me adopt", status,
                null, NOW, NOW);
    }
}
