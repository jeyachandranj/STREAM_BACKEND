package com.example.analyticsproducer.events;

import com.example.analyticsproducer.enums.EventTypeEnum;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
@Data
@ToString
public class OrderEvent implements Serializable {
    private EventTypeEnum eventType;
    private String orderId;
    private String driverId;
    private String restaurantId;
    private String customerId;
    private Date actionTime;

}
