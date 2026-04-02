package com.backend.consumer.adapter.in.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaConsumerConfigTest {

    @Test
    void shouldCreateConsumerPropsWithExpectedConfiguration() {

        KafkaConsumerConfig config = new KafkaConsumerConfig();

        Map<String, Object> props = config.consumerProps();

        assertNotNull(props);

        assertEquals(
            "localhost:9092",
            props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)
        );

        assertEquals(
            "statement-consumer-group",
            props.get(ConsumerConfig.GROUP_ID_CONFIG)
        );

        assertEquals(
            StringDeserializer.class,
            props.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)
        );

        assertEquals(
            org.springframework.kafka.support.serializer.JsonDeserializer.class,
            props.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)
        );

        assertEquals(
            "*",
            props.get(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES)
        );
    }
}