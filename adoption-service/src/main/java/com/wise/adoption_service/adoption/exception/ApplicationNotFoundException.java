package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.NotFoundException;

public class ApplicationNotFoundException extends NotFoundException {
    public ApplicationNotFoundException(Long id) {
        super("Application with id " + id + " not found");
    }
}

