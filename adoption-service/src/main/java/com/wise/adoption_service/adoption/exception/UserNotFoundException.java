package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " not found");
    }
}
