package com.example.service.orderservice.web.controller;

import com.example.service.orderservice.event.OrderEvent;
import com.example.service.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderKafkaController {

    @Value("${app.kafka.kafkaOrderTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> sendOrder(@RequestBody Order order) {
        OrderEvent event = new OrderEvent();
        event.setProduct(order.getProduct());
        event.setQuantity(order.getQuantity());

        kafkaTemplate.send(topicName, event);

        return ResponseEntity.ok().body("Order sent to kafka!");
    }
}
