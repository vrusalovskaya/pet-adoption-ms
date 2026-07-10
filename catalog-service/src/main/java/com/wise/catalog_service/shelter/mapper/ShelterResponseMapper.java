package com.wise.catalog_service.shelter.mapper;

import com.wise.catalog_service.shelter.api.ShelterResponse;
import com.wise.catalog_service.shelter.domain.Shelter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShelterResponseMapper {
    ShelterResponse toResponse(Shelter shelter);
}
