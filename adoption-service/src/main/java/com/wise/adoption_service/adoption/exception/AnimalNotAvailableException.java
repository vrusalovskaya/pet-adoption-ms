package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.ConflictException;

public class AnimalNotAvailableException extends ConflictException {
    public AnimalNotAvailableException(Long animalId) {
        super("Animal with id %d is not available for adoption".formatted(animalId));
    }
}
