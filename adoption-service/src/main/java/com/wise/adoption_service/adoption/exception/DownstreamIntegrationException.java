package com.wise.adoption_service.adoption.exception;

public class DownstreamIntegrationException extends RuntimeException {
    public DownstreamIntegrationException(String message) {
        super(message);
    }
}