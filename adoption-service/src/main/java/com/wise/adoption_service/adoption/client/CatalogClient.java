package com.wise.adoption_service.adoption.client;

import com.wise.adoption_service.adoption.exception.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
public class CatalogClient {
    private final CatalogApiClient catalogApiClient;

    @CircuitBreaker(name = "catalog-get", fallbackMethod = "ensureAnimalExistsFallback")
    public void ensureExists(Long animalId) {
        catalogApiClient.checkAnimalExists(animalId);
    }

    @CircuitBreaker(name = "catalog-reserve", fallbackMethod = "reserveAnimalFallback")
    public void reserveIfAvailable(Long animalId) {
        catalogApiClient.reserveIfAvailable(animalId);
    }

    public void ensureAnimalExistsFallback(Long animalId, Throwable throwable) {
        if (throwable instanceof HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new AnimalNotFoundException(animalId);
                case 401, 403 -> throw new DownstreamAccessDeniedException("catalog-service");
                case 400 -> throw new DownstreamIntegrationException
                        ("Catalog service rejected request: " + e.getResponseBodyAsString());
                default -> throw e;
            }
        }

        throw new CatalogUnavailableException(animalId, throwable);
    }

    public void reserveAnimalFallback(Long animalId, Throwable throwable) {
        if (throwable instanceof HttpClientErrorException e) {
            switch (e.getStatusCode().value()) {
                case 409 -> throw new AnimalNotAvailableException(animalId);
                case 404 -> throw new AnimalNotFoundException(animalId);
                case 401, 403 -> throw new DownstreamAccessDeniedException("catalog-service");
                case 400 -> throw new DownstreamIntegrationException
                        ("Catalog service rejected request: " + e.getResponseBodyAsString());
                default -> throw e;
            }
        }

        throw new CatalogUnavailableException(animalId, throwable);
    }
}
