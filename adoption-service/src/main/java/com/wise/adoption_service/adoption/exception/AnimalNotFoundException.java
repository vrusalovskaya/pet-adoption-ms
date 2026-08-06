package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.NotFoundException;

public class AnimalNotFoundException extends NotFoundException {
    public AnimalNotFoundException(Long id) {
        super("Animal with id " + id + " not found");
    }
}
