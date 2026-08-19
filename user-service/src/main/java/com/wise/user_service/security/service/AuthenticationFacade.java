package com.wise.user_service.security.service;

import com.wise.user_service.security.domain.*;

public interface AuthenticationFacade {
    AuthenticationResult login(LoginCommand command);

    AuthenticationResult register(RegisterCommand command);

    AuthenticationResult refresh(RefreshCommand command);

    void logout(LogoutCommand command);

    void logoutEverywhere(Long userId);
}
