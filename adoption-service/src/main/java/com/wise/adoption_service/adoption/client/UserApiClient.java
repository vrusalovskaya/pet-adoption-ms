package com.wise.adoption_service.adoption.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;

public interface UserApiClient {
    @HttpExchange(method = "HEAD", url = "/internal/v1/users/{id}")
    void checkUserExists(@PathVariable("id") Long id);
}
