package com.wise.adoption_service.adoption.mapper;

import com.wise.adoption_service.adoption.domain.Application;
import com.wise.adoption_service.adoption.domain.CreateApplicationCommand;
import com.wise.adoption_service.adoption.persistence.ApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationEntityMapper {

    Application toModel(ApplicationEntity entity);

    ApplicationEntity toEntity(CreateApplicationCommand command);
}
