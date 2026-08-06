package com.wise.adoption_service.adoption.mapper;

import com.wise.adoption_service.adoption.api.ApplicationResponse;
import com.wise.adoption_service.adoption.domain.Application;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationResponseMapper {

    ApplicationResponse toResponse(Application application);
}
