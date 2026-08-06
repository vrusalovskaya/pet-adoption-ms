package com.wise.adoption_service.adoption.client;

import com.wise.adoption_service.adoption.exception.DownstreamAccessDeniedException;
import com.wise.adoption_service.adoption.exception.DownstreamIntegrationException;
import com.wise.adoption_service.adoption.exception.UserNotFoundException;
import com.wise.adoption_service.adoption.exception.UsersUnavailableException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class UserClient {
    private final UserApiClient userApiClient;

    @CircuitBreaker(name = "users-get", fallbackMethod = "ensureExistsFallback")
    public void ensureExists(Long userId) {
        userApiClient.checkUserExists(userId);
    }

    public void ensureExistsFallback(Long userId, Throwable throwable) {
        if (throwable instanceof HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new UserNotFoundException(userId);
                case 401, 403 -> throw new DownstreamAccessDeniedException("user-service");
                case 400 -> throw new DownstreamIntegrationException
                        ("User service rejected request: " + e.getResponseBodyAsString());
                default -> throw e;
            }
        }

        throw new UsersUnavailableException(userId, throwable);
    }
}
