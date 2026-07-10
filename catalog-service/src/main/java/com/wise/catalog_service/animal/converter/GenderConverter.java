package com.wise.catalog_service.animal.converter;

import com.wise.catalog_service.animal.common.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String source) {

        if (source == null || source.isBlank()) {
            return null;
        }

        try {
            return Gender.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Invalid gender: '" + source + "'. Allowed values: MALE, FEMALE"
            );
        }
    }
}

