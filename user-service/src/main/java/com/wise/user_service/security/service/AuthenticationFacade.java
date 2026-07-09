package com.wise.user_service.security.service;

import com.wise.user_service.security.domain.AuthenticationResult;
import com.wise.user_service.security.domain.LoginCommand;
import com.wise.user_service.security.domain.RegisterCommand;
import com.wise.user_service.security.domain.LogoutCommand;
import com.wise.user_service.security.domain.RefreshCommand;

public interface AuthenticationFacade {
    AuthenticationResult login(LoginCommand command);

    AuthenticationResult register(RegisterCommand command);

    AuthenticationResult refresh(RefreshCommand command);

    void logout(LogoutCommand command);

    void logoutEverywhere(Long userId);
}
