package com.wise.catalog_service.animal.exception;

public class AnimalPhotoException extends RuntimeException {
    public AnimalPhotoException(String message) {
        super(message);
    }

    public AnimalPhotoException(String message, Throwable cause) {
        super(message, cause);
    }
}
