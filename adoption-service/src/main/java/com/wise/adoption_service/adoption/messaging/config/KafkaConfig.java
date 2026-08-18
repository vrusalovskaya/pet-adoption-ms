package com.wise.adoption_service.adoption.messaging.config;

import com.wise.adoption_service.adoption.messaging.ApplicationRevokedV1;
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
    public ProducerFactory<String, ApplicationRevokedV1> applicationRevokedProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, ApplicationRevokedV1> kafkaTemplate(
            ProducerFactory<String, ApplicationRevokedV1> applicationRevokedProducerFactory
    ) {
        return new KafkaTemplate<>(applicationRevokedProducerFactory);
    }
}