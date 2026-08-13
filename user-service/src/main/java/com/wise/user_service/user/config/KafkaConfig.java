package com.wise.user_service.user.config;

import com.wise.user_service.user.events.UserDeletedV1;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, UserDeletedV1> userDeletedProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, UserDeletedV1> kafkaTemplate(
            ProducerFactory<String, UserDeletedV1> userDeletedProducerFactory
    ) {
        return new KafkaTemplate<>(userDeletedProducerFactory);
    }
}
