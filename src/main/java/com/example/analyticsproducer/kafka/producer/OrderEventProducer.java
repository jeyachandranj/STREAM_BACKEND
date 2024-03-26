package com.example.analyticsproducer.kafka.producer;

import com.example.analyticsproducer.events.OrderEvent;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private static final String TOPIC = "order-events";
    Logger logger = LoggerFactory.getLogger(OrderEventProducer.class);


    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;
    public void sendOrderEvent(OrderEvent orderEvent) {
        try{
        kafkaTemplate.send(TOPIC, orderEvent).get();
        } catch (Exception executionException) {
            logger.error(TOPIC, executionException);
        }
    }
}
