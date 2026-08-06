package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.ServiceUnavailableException;

public class CatalogUnavailableException extends ServiceUnavailableException {
    public CatalogUnavailableException(Long animalId, Throwable cause) {
        super("Catalog", animalId, cause);
    }
}
