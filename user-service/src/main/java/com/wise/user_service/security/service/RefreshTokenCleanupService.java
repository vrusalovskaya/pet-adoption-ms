package com.wise.user_service.security.service;

import com.wise.user_service.security.persistence.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenCleanupService {
    private final RefreshTokenRepository repository;

    @Transactional
    public void deleteExpiredTokens() {
        repository.deleteAllByExpiresAtBefore(Instant.now());
    }
}
