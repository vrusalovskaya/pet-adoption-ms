package com.wise.user_service.support;

import com.wise.user_service.security.domain.SecurityUser;
import com.wise.user_service.user.common.Role;
import com.wise.user_service.user.domain.User;
import com.wise.user_service.user.persistence.UserEntity;

import java.time.Instant;

public final class TestFixtures {

    public static final Instant NOW = Instant.parse("2026-01-01T12:00:00.00Z");

    private TestFixtures() {
    }

    public static UserEntity userEntity(Long id, String email, Role role) {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setEmail(email);
        entity.setPasswordHash("hashed-secret");
        entity.setFirstName("Jane");
        entity.setLastName("Doe");
        entity.setPhone("+15551234567");
        entity.setRole(role);
        entity.setCreatedAt(NOW);
        return entity;
    }

    public static User user(Long id, String email, Role role) {
        return new User(id, email, "hashed-secret", "Jane", "Doe", "+15551234567", role, NOW);
    }

    public static SecurityUser securityUser(Long id, String email, Role role) {
        return new SecurityUser(id, email, "hashed-secret", "Jane", "Doe", role);
    }
}
