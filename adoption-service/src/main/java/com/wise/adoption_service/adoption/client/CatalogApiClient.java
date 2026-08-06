package com.wise.adoption_service.adoption.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

public interface CatalogApiClient {
    @HttpExchange(method = "HEAD", url = "/internal/v1/animals/{id}")
    void checkAnimalExists(@PathVariable("id") Long id);

    @PatchExchange(url = "/internal/v1/animals/{id}/reserve")
    void reserveIfAvailable(@PathVariable("id") Long id);
}
