package com.wise.adoption_service.shared.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String serviceName, Long id, Throwable cause) {
        super(serviceName + " service unavailable while getting entity " + id, cause);
    }
}
