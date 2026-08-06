package com.wise.adoption_service.adoption.domain;

public record CreateApplicationCommand(
        Long animalId,
        Long applicantId,
        String message
) {
}
