package com.wise.catalog_service.animal.domain;

import com.wise.catalog_service.shared.storage.model.StorageType;

public record PhotoMetadataModel(
        String photoFileId,
        StorageType storageType,
        String photoContentType,
        String photoFilename,
        Long sizeBytes
) {
}
