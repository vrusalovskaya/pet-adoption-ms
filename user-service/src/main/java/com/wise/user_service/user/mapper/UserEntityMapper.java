package com.wise.user_service.user.mapper;

import com.wise.user_service.user.domain.CreateUserCommand;
import com.wise.user_service.user.domain.UpdateProfileCommand;
import com.wise.user_service.user.domain.User;
import com.wise.user_service.user.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserEntityMapper {

    UserEntity toEntity(CreateUserCommand command);

    User toModel(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntity(UpdateProfileCommand command, @MappingTarget UserEntity entity);
}
