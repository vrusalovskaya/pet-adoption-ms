package com.wise.adoption_service.adoption.mapper;


import com.wise.adoption_service.adoption.messaging.ApplicationRevokedV1;
import com.wise.adoption_service.adoption.persistence.ApplicationRevokedOutboxEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationRevokedEventMapper {

    ApplicationRevokedV1 toEvent(ApplicationRevokedOutboxEntity entity);
}
