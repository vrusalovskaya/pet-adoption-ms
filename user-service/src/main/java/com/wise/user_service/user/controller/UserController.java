package com.wise.user_service.user.controller;

import com.wise.user_service.security.domain.SecurityUser;
import com.wise.user_service.user.domain.ChangePasswordCommand;
import com.wise.user_service.user.domain.UpdateProfileCommand;
import com.wise.user_service.user.domain.User;
import com.wise.user_service.user.mapper.UserResponseMapper;
import com.wise.user_service.user.service.UserService;
import com.wise.user_service.user.api.ChangePasswordRequest;
import com.wise.user_service.user.api.UpdateProfileRequest;
import com.wise.user_service.user.api.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserResponseMapper responseMapper;

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateProfile(@AuthenticationPrincipal SecurityUser user,
                                      @Valid @RequestBody UpdateProfileRequest request
    ) {
        User updated = userService.updateProfile(toCommand(user.getUserId(), request));
        UserResponse userResponse = responseMapper.toResponse(updated);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal SecurityUser user,
                                               @Valid @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(toCommand(user.getUserId(), request));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal SecurityUser user) {
        userService.delete(user.getUserId());
        return ResponseEntity.noContent().build();
    }

    private UpdateProfileCommand toCommand(Long id, UpdateProfileRequest request) {
        return new UpdateProfileCommand(id, request.email(), request.firstName(),
                request.lastName(), request.phone());
    }

    private ChangePasswordCommand toCommand(Long id, ChangePasswordRequest request) {
        return new ChangePasswordCommand(id, request.oldPassword(), request.newPassword());
    }
}
