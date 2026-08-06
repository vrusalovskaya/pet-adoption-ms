package com.wise.adoption_service.adoption.exception;

public class DownstreamAccessDeniedException extends RuntimeException {
    public DownstreamAccessDeniedException(String serviceName) {
        super("Access denied while calling downstream service: " + serviceName);
    }
}