package com.wise.catalog_service.animal.converter;

import com.wise.catalog_service.animal.common.AnimalStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AnimalStatusConverter implements Converter<String, AnimalStatus> {

    @Override
    public AnimalStatus convert(String source) {

        if (source == null || source.isBlank()) {
            return null;
        }

        try {
            return AnimalStatus.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid animal status: '" + source + "'. Allowed values: AVAILABLE, RESERVED, ADOPTED, WITHDRAWN"
            );
        }
    }
}
