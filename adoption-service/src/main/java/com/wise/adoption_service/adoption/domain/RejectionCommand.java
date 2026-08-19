package com.wise.adoption_service.adoption.domain;

public record RejectionCommand(
        Long id,
        String decisionComment
) {
}
