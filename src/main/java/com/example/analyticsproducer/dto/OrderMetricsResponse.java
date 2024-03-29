package com.example.analyticsproducer.dto;

public class OrderMetricsResponse {

    private Long totalOrder;
    private Long totalRestaurant;

    private Long totalDriver;
    private Long totalCustomer;
    private Long successOrder;
    private Long failureOrder;

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Long getTotalRestaurant() {
        return totalRestaurant;
    }

    public void setTotalRestaurant(Long totalRestaurant) {
        this.totalRestaurant = totalRestaurant;
    }

    public Long getTotalDriver() {
        return totalDriver;
    }

    public void setTotalDriver(Long totalDriver) {
        this.totalDriver = totalDriver;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Long getSuccessOrder() {
        return successOrder;
    }

    public void setSuccessOrder(Long successOrder) {
        this.successOrder = successOrder;
    }

    public Long getFailureOrder() {
        return failureOrder;
    }

    public void setFailureOrder(Long failureOrder) {
        this.failureOrder = failureOrder;
    }
}
