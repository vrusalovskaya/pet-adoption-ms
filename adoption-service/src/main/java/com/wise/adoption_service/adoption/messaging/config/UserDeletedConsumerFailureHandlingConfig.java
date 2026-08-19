package com.wise.adoption_service.adoption.messaging.config;

import com.wise.adoption_service.adoption.messaging.UserDeletedV1;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DelegatingByTypeSerializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class UserDeletedConsumerFailureHandlingConfig {

    @Bean
    KafkaTemplate<String, Object> userDeletedDltKafkaTemplate(KafkaProperties kafkaProperties) {
        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();

        Map<Class<?>, Serializer<?>> serializers = new LinkedHashMap<>();
        serializers.put(byte[].class, new ByteArraySerializer());
        serializers.put(Object.class, new JacksonJsonSerializer<>());

        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(
                producerProperties,
                new StringSerializer(),
                new DelegatingByTypeSerializer(serializers, true)
        );

        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    DefaultErrorHandler userDeletedErrorHandler(
            KafkaTemplate<String, Object> userDeletedDltKafkaTemplate,
            KafkaTopicsProperties kafkaTopicsProperties
    ) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                userDeletedDltKafkaTemplate,
                (consumerRecord, exception) -> new TopicPartition(
                        kafkaTopicsProperties.getUserDeletedDlt(),
                        consumerRecord.partition()
                )
        );

        recoverer.setFailIfSendResultIsError(true);
        recoverer.setLogRecoveryRecord(true);

        return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 2L));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, UserDeletedV1>
    userDeletedKafkaListenerContainerFactory(
            ConsumerFactory<String, UserDeletedV1> consumerFactory,
            DefaultErrorHandler userDeletedErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, UserDeletedV1> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(userDeletedErrorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

        return factory;
    }
}
