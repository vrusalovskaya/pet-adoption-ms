package com.wise.catalog_service.animal.messaging.config;

import com.wise.catalog_service.animal.messaging.ApplicationRevokedV1;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(KafkaTopicsProperties.class)
public class ApplicationRevokedConsumerFailureHandlingConfig {
    @Bean
    KafkaTemplate<String, Object> applicationRevokedDltKafkaTemplate(KafkaProperties kafkaProperties) {
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
    DefaultErrorHandler applicationRevokedErrorHandler(
            KafkaTemplate<String, Object> applicationRevokedDltKafkaTemplate,
            KafkaTopicsProperties kafkaTopicsProperties
    ) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                applicationRevokedDltKafkaTemplate,
                (record, exception) -> new TopicPartition(
                        kafkaTopicsProperties.getApplicationRevokedDlt(),
                        record.partition()
                )
        );

        recoverer.setFailIfSendResultIsError(true);
        recoverer.setLogRecoveryRecord(true);

        return new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 2L));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ApplicationRevokedV1>
    applicationRevokedKafkaListenerContainerFactory(
            ConsumerFactory<String, ApplicationRevokedV1> consumerFactory,
            DefaultErrorHandler applicationRevokedErrorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, ApplicationRevokedV1> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(applicationRevokedErrorHandler);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

        return factory;
    }
}
