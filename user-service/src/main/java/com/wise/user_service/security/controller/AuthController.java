package com.wise.user_service.security.controller;

import com.wise.user_service.security.api.*;
import com.wise.user_service.security.domain.*;
import com.wise.user_service.security.service.AuthenticationFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationFacade auth;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        AuthenticationResult result = auth.login(toCommand(request));
        return new AuthResponse(result.accessToken(), result.refreshToken());
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResult result = auth.register(toCommand(request));
        return new AuthResponse(result.accessToken(), result.refreshToken());
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        AuthenticationResult result = auth.refresh(toCommand(request));
        return new AuthResponse(result.accessToken(), result.refreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        auth.logout(toCommand(request));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutEverywhere(@AuthenticationPrincipal SecurityUser user) {
        auth.logoutEverywhere(user.getUserId());
        return ResponseEntity.noContent().build();
    }

    private RegisterCommand toCommand(RegisterRequest request) {
        return new RegisterCommand(request.email(), request.password(), request.firstName(),
                request.lastName(), request.phone());
    }

    private LoginCommand toCommand(LoginRequest request) {
        return new LoginCommand(request.email(), request.password());
    }

    private RefreshCommand toCommand(RefreshRequest request) {
        return new RefreshCommand(request.refreshToken());
    }

    private LogoutCommand toCommand(LogoutRequest request) {
        return new LogoutCommand(request.refreshToken());
    }
}
