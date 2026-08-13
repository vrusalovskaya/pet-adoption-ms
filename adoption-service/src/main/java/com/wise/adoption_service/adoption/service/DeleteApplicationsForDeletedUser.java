package com.wise.adoption_service.adoption.service;

public interface DeleteApplicationsForDeletedUser {
    void deleteByApplicantId(Long applicantId);
}
