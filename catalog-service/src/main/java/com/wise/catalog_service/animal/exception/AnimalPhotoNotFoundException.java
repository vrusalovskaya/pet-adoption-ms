package com.wise.catalog_service.animal.exception;


import com.wise.catalog_service.shared.exception.NotFoundException;

public class AnimalPhotoNotFoundException extends NotFoundException {
    public AnimalPhotoNotFoundException(Long animalId) {
        super("Photo for animal with id " + animalId + " not found");
    }
}
