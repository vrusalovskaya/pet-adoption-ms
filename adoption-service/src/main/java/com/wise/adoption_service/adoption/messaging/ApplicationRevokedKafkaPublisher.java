package com.wise.adoption_service.adoption.messaging;

import com.wise.adoption_service.adoption.config.KafkaTopicsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class ApplicationRevokedKafkaPublisher {

    private final KafkaTemplate<String, ApplicationRevokedV1> kafkaTemplate;
    private final KafkaTopicsProperties kafkaProperties;

    public void publish(ApplicationRevokedV1 event) {
        String topic = kafkaProperties.getApplicationRevoked();
        try {
            kafkaTemplate.send(topic, event.animalId().toString(), event).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Kafka publication was interrupted", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Kafka publication failed", exception.getCause());
        }
    }
}