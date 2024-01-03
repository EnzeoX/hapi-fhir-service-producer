package com.job.testsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class KafkaProducer {

    @Value(value = "${application.kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKafkaMessage(String message) {
        Objects.requireNonNull(message, "Provided Kafka message for sending is null or empty");
        this.kafkaTemplate.send(kafkaTopic, message);
    }

    public void sendMultipleKafkaMessages(List<String> data) {
        Objects.requireNonNull(data, "Provided list of elements is null or empty");
        data.forEach(obj -> {
            log.info(obj);
            sendKafkaMessage(obj);
        });
    }
}
