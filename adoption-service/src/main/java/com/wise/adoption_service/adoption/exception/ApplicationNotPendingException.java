package com.wise.adoption_service.adoption.exception;

import com.wise.adoption_service.adoption.common.ApplicationStatus;
import com.wise.adoption_service.shared.exception.ConflictException;

public class ApplicationNotPendingException extends ConflictException {

    public ApplicationNotPendingException(ApplicationStatus currentStatus) {
        super("Application must be in PENDING status, current status is %s"
                .formatted(currentStatus));
    }
}
