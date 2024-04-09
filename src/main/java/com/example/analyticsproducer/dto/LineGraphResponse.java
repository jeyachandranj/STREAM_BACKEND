package com.example.analyticsproducer.dto;

import java.util.List;

public class LineGraphResponse {
    List<Series> userSeries;
    List<Series> orderSeries;

    public List<Series> getUserSeries() {
        return userSeries;
    }

    public void setUserSeries(List<Series> userSeries) {
        this.userSeries = userSeries;
    }

    public List<Series> getOrderSeries() {
        return orderSeries;
    }

    public void setOrderSeries(List<Series> orderSeries) {
        this.orderSeries = orderSeries;
    }
}


