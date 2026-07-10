package com.wise.catalog_service.animal.exception;


import com.wise.catalog_service.shared.exception.NotFoundException;

public class AnimalNotFoundException extends NotFoundException {
    public AnimalNotFoundException(Long id) {
        super("Animal with id " + id + " not found");
    }
}
