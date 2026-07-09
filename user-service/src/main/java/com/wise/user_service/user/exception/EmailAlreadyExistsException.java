package com.wise.user_service.user.exception;

import com.wise.user_service.shared.exception.ConflictException;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String email) {
        super("Email '%s' is already in use".formatted(email));
    }
}