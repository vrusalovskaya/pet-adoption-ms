package com.wise.catalog_service.shelter.exception;


import com.wise.catalog_service.shared.exception.NotFoundException;

public class ShelterNotFoundException extends NotFoundException {

    public ShelterNotFoundException(Long id) {
        super("Shelter with id " + id + " not found");
    }
}
