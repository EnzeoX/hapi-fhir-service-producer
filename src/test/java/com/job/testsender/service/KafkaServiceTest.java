package com.job.testsender.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaServiceTest {

    private KafkaService kafkaServiceMock;

    private KafkaService prodConKafkaService;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private ConsumerFactory<String, String> kafkaTestConsumer;

    private BlockingQueue<ConsumerRecord<String, String>> records;

    private KafkaMessageListenerContainer<String, String> containerListener;

    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Map<String, Object> getConsumerProperties() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ConsumerConfig.GROUP_ID_CONFIG, "consumer",
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10",
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringDeserializer.class,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    private Map<String, Object> getProducerProperties() {
        return Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
    }

    @BeforeAll
    public void init() {
        kafkaServiceMock = new KafkaService(kafkaTemplate);
        embeddedKafkaBroker = new EmbeddedKafkaBroker(1, false, 2, "hapi-fhir-topic");
        embeddedKafkaBroker.brokerProperties(Map.of("listeners", "PLAINTEXT://localhost:9092"));
        embeddedKafkaBroker.afterPropertiesSet();
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(getProducerProperties());
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(getConsumerProperties());
        ContainerProperties containerProperties = new ContainerProperties("hapi-fhir-topic");
        containerListener = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingDeque<>();
        containerListener.setupMessageListener((MessageListener<String, String>) records::add);
        containerListener.start();
        ContainerTestUtils.waitForAssignment(containerListener, embeddedKafkaBroker.getPartitionsPerTopic());
        prodConKafkaService = new KafkaService(new KafkaTemplate<>(producerFactory));
        ReflectionTestUtils.setField(prodConKafkaService, "kafkaTopic", "hapi-fhir-topic");
    }

    @Test
    public void kafkaTest_Send_simple_message() throws InterruptedException {
        String testData = "TEST BUNDLE STRING";
        prodConKafkaService.sendKafkaMessage(testData);
        ConsumerRecord<String, String> message = records.poll(1000, TimeUnit.MILLISECONDS);
        assertNotNull(message);
        assertEquals(testData, message.value());
    }

    @Test
    public void kafkaTest_Send_list_of_messages() throws InterruptedException {
        List<String> values = List.of("VALUE1");
        prodConKafkaService.sendMultipleKafkaMessages(values);
        ConsumerRecord<String, String> message = records.poll(1000, TimeUnit.MILLISECONDS);
        assertNotNull(message);
        assertEquals(values.get(0), message.value());
    }

    @Test
    public void kafkaTest_Empty_message_provided() {
        NullPointerException nullEx = assertThrows(NullPointerException.class,
                () -> kafkaServiceMock.sendKafkaMessage(""));
        assertEquals("Provided Kafka message for sending is null or empty", nullEx.getMessage());
    }

    @Test
    public void kafkaTest_Null_message_provided() {
        NullPointerException nullEx = assertThrows(NullPointerException.class,
                () -> kafkaServiceMock.sendKafkaMessage(null));
        assertEquals("Provided Kafka message for sending is null or empty", nullEx.getMessage());
    }

    @AfterAll
    void tearDown() {
        containerListener.stop();
    }
}
