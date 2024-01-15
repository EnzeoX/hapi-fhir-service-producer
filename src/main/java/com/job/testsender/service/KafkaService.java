package com.job.testsender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KafkaService {

    @Value(value = "${application.kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendKafkaMessage(String message) {
        if (message == null || message.isEmpty()) throw new NullPointerException("Provided Kafka message for sending is null or empty");
        this.kafkaTemplate.send(kafkaTopic, message);
    }

    public void sendMultipleKafkaMessages(List<String> data) {
        if (data == null || data.isEmpty()) throw new NullPointerException("Provided list of elements is null or empty");
        log.info(String.valueOf(data.size()));
        data.forEach(this::sendKafkaMessage);
    }
}
