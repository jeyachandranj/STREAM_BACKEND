package com.example.analyticsproducer.dto;

public class OrderMetricsResponse {

    private Long totalOrder;
    private Long successOrder;
    private Long failureOrder;
    private String avgWaitTime;

    public Long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Long totalOrder) {
        this.totalOrder = totalOrder;
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

    public String getAvgWaitTime() {
        return avgWaitTime;
    }

    public void setAvgWaitTime(String avgWaitTime) {
        this.avgWaitTime = avgWaitTime;
    }
}
