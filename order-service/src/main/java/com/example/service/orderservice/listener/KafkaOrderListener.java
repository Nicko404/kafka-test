package com.example.service.orderservice.listener;

import com.example.service.orderservice.event.OrderStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class KafkaOrderListener {

    @KafkaListener(
            topics = "${app.kafka.kafkaOrderStatusTopic}",
            groupId = "${app.kafka.kafkaOrderGroupId}",
            containerFactory = "kafkaOrderConcurrentKafkaListenerContainerFactory"
    )
    public void listen(
            @Payload OrderStatusEvent orderStatusEvent,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
            ) {

        log.info("Received message: {}", orderStatusEvent.toString());
        log.info("Key: {}, Partition: {}, Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
    }
}
