package com.wise.adoption_service.adoption.service;

import com.wise.adoption_service.adoption.common.ApplicationStatus;
import com.wise.adoption_service.adoption.domain.Application;
import com.wise.adoption_service.adoption.domain.CreateApplicationCommand;
import com.wise.adoption_service.adoption.domain.RejectionCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {
    Application create(CreateApplicationCommand command);

    Page<Application> getAllByApplicant(Long applicantId, Pageable pageable);

    Page<Application> getAll(ApplicationStatus status, Long animalId, Long applicantId,
                             Pageable pageable);

    Application getForAdmin(Long id);

    Application getForUser(Long id, Long userId);

    Application approve(Long id);

    Application reject(RejectionCommand command);

    Application cancel(Long id, Long userId);
}
