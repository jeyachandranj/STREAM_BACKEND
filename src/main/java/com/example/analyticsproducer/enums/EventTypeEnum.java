package com.example.analyticsproducer.enums;

import java.io.Serializable;

import lombok.Getter;
@Getter
public enum EventTypeEnum implements Serializable {
    CUSTOMER_ORDER_FOOD,
    DRIVER_ACCEPT,
    DRIVER_REJECT,
    PICK_UP_FROM_RESTAURANT,
    DROP_UP_TO_CUSTOMER,
    CUSTOMER_REJECT
}
