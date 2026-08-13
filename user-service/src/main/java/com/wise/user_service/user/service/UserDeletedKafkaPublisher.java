package com.wise.user_service.user.service;

import com.wise.user_service.user.config.KafkaTopicsProperties;
import com.wise.user_service.user.events.UserDeletedV1;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class UserDeletedKafkaPublisher {

    private final KafkaTemplate<String, UserDeletedV1> kafkaTemplate;
    private final KafkaTopicsProperties kafkaProperties;

    public void publish(UserDeletedV1 event) {
        String topic = kafkaProperties.getUserDeleted();
        try {
            kafkaTemplate.send(topic, event.userId().toString(), event).get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Kafka publication was interrupted", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Kafka publication failed", exception.getCause());
        }
    }
}
