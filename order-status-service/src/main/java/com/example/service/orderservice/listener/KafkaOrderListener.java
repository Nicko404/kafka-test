package com.example.service.orderservice.listener;

import com.example.service.orderservice.event.OrderStatusEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderListener {

    @Value("${app.kafka.kafkaOrderStatusTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    @KafkaListener(
            topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaOrderGroupId}",
            containerFactory = "kafkaOrderConcurrentKafkaListenerContainerFactory"
    )
    public void listen() {
        log.info("Received kafka order message!");

        OrderStatusEvent orderStatusEvent = new OrderStatusEvent();
        orderStatusEvent.setStatus("CREATED");
        orderStatusEvent.setDate(Instant.now());

        kafkaTemplate.send(topicName, orderStatusEvent);
    }
}
