package com.wise.user_service.user.controller;

import com.wise.user_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/v1/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    public ResponseEntity<Void> checkUserExists(@PathVariable Long id) {
        boolean exists = userService.existsById(id);
        return exists ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
