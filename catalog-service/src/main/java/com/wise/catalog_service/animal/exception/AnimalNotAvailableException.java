package com.wise.catalog_service.animal.exception;

import com.wise.catalog_service.shared.exception.ConflictException;

public class AnimalNotAvailableException extends ConflictException {
    public AnimalNotAvailableException(Long animalId) {
        super("Animal with id %d is not available for adoption".formatted(animalId));
    }
}
