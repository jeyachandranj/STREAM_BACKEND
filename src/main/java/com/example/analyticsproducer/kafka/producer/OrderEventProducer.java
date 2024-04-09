package com.example.analyticsproducer.kafka.producer;

import com.example.analyticsproducer.enums.EventTypeEnum;
import com.example.analyticsproducer.events.OrderEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
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
    private static final Random random = new Random();

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(Integer orderCount) {
        try {
            for (int i = 0; i < orderCount; i++) {
                OrderEvent baseOrderEvent = getBaseOrderEvent();
                Date lastEventTime = generateRandomTime();
                for (EventTypeEnum eventType : EventTypeEnum.values()) {
                    if(eventType.equals(EventTypeEnum.CUSTOMER_REJECT)){
                        break;
                    }
                    if (shouldRejectEvent(eventType, i)) {
                        baseOrderEvent.setEventType(EventTypeEnum.CUSTOMER_REJECT);
                        baseOrderEvent
                                .setActionTime(getActionTimeByEventType(lastEventTime, EventTypeEnum.CUSTOMER_REJECT));
                        lastEventTime = baseOrderEvent.getActionTime();
                        kafkaTemplate.send(TOPIC, baseOrderEvent.getOrderId(),baseOrderEvent).get();
                        break;
                    } else {
                        baseOrderEvent.setEventType(eventType);
                        baseOrderEvent.setActionTime(getActionTimeByEventType(lastEventTime, eventType));
                        lastEventTime = baseOrderEvent.getActionTime();
                        kafkaTemplate.send(TOPIC, baseOrderEvent.getOrderId(), baseOrderEvent).get();
                    }

                }
            }
        } catch (Exception executionException) {
            logger.error(TOPIC, executionException);
        }

    }

    private Date getActionTimeByEventType(Date lastEventTime, EventTypeEnum eventType) {
        switch (eventType) {
            case CUSTOMER_ORDER_FOOD:
                return lastEventTime;
            case DRIVER_ACCEPT:
                return new Date(lastEventTime.getTime() + random.nextInt(100000)); // 2 minutes after customer order
            case PICK_UP_FROM_RESTAURANT:
                return new Date(lastEventTime.getTime() + random.nextInt(500000) + 500000); // 10 to 20 minutes after
                                                                                            // customer order
            case DROP_UP_TO_CUSTOMER, CUSTOMER_REJECT:

                return new Date(lastEventTime.getTime() + random.nextInt(1500000) + 500000); // 10 to 40 minutes after
                                                                                             // customer order
            default:
                return new Date();
        }
    }

    private OrderEvent getBaseOrderEvent() {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId("Order" + UUID.randomUUID());
        orderEvent.setDriverId("Driver" + random.nextInt(1000));
        orderEvent.setRestaurantId("Restaurant" + random.nextInt(50));
        orderEvent.setCustomerId("Customer" + random.nextInt(1000000));
        return orderEvent;
    }

    public static Date generateRandomTime() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // Generate random hour
        Random random = new Random();
        int randomHour = random.nextInt(24); // Generates a random integer between 0 and 23 (inclusive)

        // Set random hour
        calendar.set(Calendar.HOUR_OF_DAY, randomHour);

        // Clear minutes, seconds, and milliseconds
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Return the generated random time
        return calendar.getTime();
    }

    public static boolean shouldRejectEvent(EventTypeEnum eventType, int i) {
        switch (eventType) {
            case DRIVER_ACCEPT:
                return i % 11 == 0;
            case PICK_UP_FROM_RESTAURANT:
                return i % 23 == 0;
            case DROP_UP_TO_CUSTOMER:
                return i % 29 == 0;
            case CUSTOMER_REJECT:
                return i % 97 == 0;
            default:
                return false;
        }

    }
}
