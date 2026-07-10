package com.wise.catalog_service.shared.storage.exception;

import com.wise.catalog_service.shared.exception.NotFoundException;

public class ImageNotFoundException extends NotFoundException {

    public ImageNotFoundException(String key) {
        super("Image not found: " + key);
    }
}
