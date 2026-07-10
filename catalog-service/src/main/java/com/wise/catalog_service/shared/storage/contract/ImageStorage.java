package com.wise.catalog_service.shared.storage.contract;

import com.wise.catalog_service.shared.storage.model.StorageType;
import com.wise.catalog_service.shared.storage.model.StoredImage;
import com.wise.catalog_service.shared.storage.model.StoredImageStream;

import java.io.InputStream;

public interface ImageStorage {
    StoredImage save(InputStream content, long sizeBytes, String contentType, String originalFilename);

    StoredImageStream load(String key);

    void delete(String key);

    StorageType type();
}
