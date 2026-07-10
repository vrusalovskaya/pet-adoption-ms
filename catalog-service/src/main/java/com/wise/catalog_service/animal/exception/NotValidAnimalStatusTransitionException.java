package com.wise.catalog_service.animal.exception;

import com.wise.catalog_service.animal.common.AnimalStatus;
import com.wise.catalog_service.shared.exception.ConflictException;

public class NotValidAnimalStatusTransitionException extends ConflictException {

    public NotValidAnimalStatusTransitionException(AnimalStatus previousStatus, AnimalStatus newStatus) {
        super("Cannot perform transition from " + previousStatus + " to "  + newStatus);
    }
}
