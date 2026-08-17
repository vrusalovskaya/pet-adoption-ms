package com.wise.user_service.user.mapper;

import com.wise.user_service.user.messaging.UserDeletedV1;
import com.wise.user_service.user.persistence.UserDeletedOutboxEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDeletedEventMapper {

    UserDeletedV1 toEvent(UserDeletedOutboxEntity entity);
}
