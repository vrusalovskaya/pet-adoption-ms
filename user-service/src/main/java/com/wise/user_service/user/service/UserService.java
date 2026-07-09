package com.wise.user_service.user.service;

import com.wise.user_service.user.domain.ChangePasswordCommand;
import com.wise.user_service.user.domain.CreateUserCommand;
import com.wise.user_service.user.domain.UpdateProfileCommand;
import com.wise.user_service.user.domain.User;

public interface UserService {
    User create(CreateUserCommand command);

    User findByEmail(String email);

    User findById(Long id);

    User updateProfile(UpdateProfileCommand request);

    void changePassword(ChangePasswordCommand command);

    void delete(Long id);
}
