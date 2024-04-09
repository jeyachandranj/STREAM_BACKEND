package com.example.analyticsproducer.database;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "order_wait_times")
public class OrderWaitTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(name = "date")
    private String date;

   
    @Column(name = "hour")
    private Integer hour;


    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "customer_wait_time", nullable = false)
    private Long customerWaitTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerWaitTime() {
        return customerWaitTime;
    }

    public void setCustomerWaitTime(Long customerWaitTime) {
        this.customerWaitTime = customerWaitTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

}