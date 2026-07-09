package com.wise.user_service.user.mapper;

import com.wise.user_service.user.domain.User;
import com.wise.user_service.user.api.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserResponseMapper {
    UserResponse toResponse(User user);
}
