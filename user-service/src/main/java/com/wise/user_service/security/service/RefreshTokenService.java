package com.wise.user_service.security.service;

import com.wise.user_service.security.domain.RefreshTokenRotationResult;

public interface RefreshTokenService {
    String create(Long userId);

    RefreshTokenRotationResult rotate(String token);

    void deleteByTokenIfExists(String token);

    void deleteByUserId(Long userId);
}
