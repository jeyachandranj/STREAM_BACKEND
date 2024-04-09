package com.example.analyticsproducer.kafka.consumer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.analyticsproducer.repository.OrderWaitTimeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.analyticsproducer.database.OrderWaitTime;
import com.example.analyticsproducer.dto.StreamOutputEvent;
import com.example.analyticsproducer.enums.EventTypeEnum;
import com.example.analyticsproducer.events.OrderEvent;
import com.example.analyticsproducer.service.EventMetricsService;
import com.example.analyticsproducer.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class CustomerWaitTime {
     private static final Logger LOGGER = LogManager.getLogger(StreamOutput.class);
    @Autowired
    private OrderWaitTimeRepository orderWaitTimeRepository;
    @Autowired
    private EventMetricsService eventMetricsService;
    private Map<String, Date> lastActionTimes = new HashMap<>();
    @KafkaListener(topics = "order-events", groupId = "wait-time")
    public void listen(String message) {
         try{
            System.out.println("Received Order event: " + message);
        ObjectMapper mapper = new ObjectMapper();
        OrderEvent orderEvent =mapper.readValue(message, OrderEvent.class);
        if (orderEvent.getEventType() == EventTypeEnum.CUSTOMER_ORDER_FOOD ||
            orderEvent.getEventType() == EventTypeEnum.DROP_UP_TO_CUSTOMER) {
            String orderId = orderEvent.getOrderId();
            Date actionTime = orderEvent.getActionTime();
            Date lastActionTime = lastActionTimes.get(orderId);
            if (lastActionTime != null) {
                if (orderEvent.getEventType() == EventTypeEnum.DROP_UP_TO_CUSTOMER) {
                    long timeDifferenceMillis = actionTime.getTime() - lastActionTime.getTime();
                    System.out.println("Time difference for order ID " + orderId + ": " + timeDifferenceMillis + " ms");
                    OrderWaitTime orderWaitTime = new OrderWaitTime();
                    orderWaitTime.setOrderId(orderId);
                    orderWaitTime.setCustomerWaitTime(timeDifferenceMillis/1000);
                    orderWaitTime.setDate(DateUtil.convertToDateFormate(actionTime.getTime()));
                    orderWaitTime.setHour(DateUtil.extractHourFromDate(actionTime));
                    orderWaitTimeRepository.save(orderWaitTime);
    lastActionTimes.remove(orderId);
                }
            }
            lastActionTimes.put(orderId, actionTime);
        }
         } catch(Exception e)
         {
            e.printStackTrace();
         }
    }
}
