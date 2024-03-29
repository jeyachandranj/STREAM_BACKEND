package com.example.analyticsproducer.controller;

import com.example.analyticsproducer.events.OrderEvent;
import com.example.analyticsproducer.kafka.producer.OrderEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/v1")
public class OrderEventController {

    @Autowired
    private OrderEventProducer orderEventProducer;
    @RequestMapping(value="/order-event",method= RequestMethod.POST)
    public ResponseEntity<Boolean> createOrder(final @RequestBody Integer orderCount)  {
       orderEventProducer.sendOrderEvent(orderCount);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
