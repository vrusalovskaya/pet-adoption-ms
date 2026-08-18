package com.wise.catalog_service.animal.messaging;

import com.wise.catalog_service.animal.service.ReleaseAnimalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationRevokedKafkaListener {
    private final ReleaseAnimalUseCase releaseAnimalUseCase;

    @KafkaListener(
            topics = "${app.kafka.topics.application-revoked}",
            groupId = "${app.kafka.groups.application-revoked}",
            containerFactory = "applicationRevokedKafkaListenerContainerFactory"
    )
    public void onApplicationRevoked(ApplicationRevokedV1 event) {
        releaseAnimalUseCase.execute(event.animalId());
    }
}
