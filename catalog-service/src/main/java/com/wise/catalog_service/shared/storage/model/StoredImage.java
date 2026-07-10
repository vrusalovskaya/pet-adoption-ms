package com.wise.catalog_service.shared.storage.model;

public record StoredImage(
        String key,
        String contentType,
        String originalFilename,
        long sizeBytes
) {
}
