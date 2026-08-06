package com.wise.adoption_service.shared;

import com.wise.adoption_service.adoption.exception.ApplicationAccessDeniedException;
import com.wise.adoption_service.adoption.exception.DownstreamAccessDeniedException;
import com.wise.adoption_service.adoption.exception.DownstreamIntegrationException;
import com.wise.adoption_service.shared.exception.ConflictException;
import com.wise.adoption_service.shared.exception.NotFoundException;
import com.wise.adoption_service.shared.exception.ServiceUnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorizationDenied(
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.FORBIDDEN,
                "You do not have permission to perform this action",
                request
        );
    }

    @ExceptionHandler(ApplicationAccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDenied(
            ApplicationAccessDeniedException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler({
            ConflictException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflict(
            Exception ex,
            HttpServletRequest request
    ) {
        String message = ex instanceof DataIntegrityViolationException
                ? "The resource already exists or violates a database constraint"
                : ex.getMessage();

        return buildResponse(
                HttpStatus.CONFLICT,
                message,
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message = ex.getCause() != null
                ? ex.getCause().getMessage()
                : String.format(
                "Invalid value '%s' for parameter '%s'",
                ex.getValue(),
                ex.getName()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request
        );
    }

    @ExceptionHandler({
            DownstreamAccessDeniedException.class,
            DownstreamIntegrationException.class
    })
    public ResponseEntity<ApiErrorResponse> handleDownstreamError(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        log.error("Downstream service communication failure: {}", ex.getMessage(), ex);

        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                "Failed to communicate with an internal dependency service.",
                request
        );
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceUnavailable(ServiceUnavailableException ex,
                                                                     HttpServletRequest request) {
        log.error("Service unavailable exception", ex);

        return buildResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "The request cannot be completed because a required service is temporarily unavailable.",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unexpected error", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                request
        );
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status)
                .body(response);
    }

}
