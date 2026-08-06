package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.shared.exception.ServiceUnavailableException;

public class UsersUnavailableException extends ServiceUnavailableException {
        public UsersUnavailableException(Long userId, Throwable cause) {
                super("User", userId, cause);
        }
}
