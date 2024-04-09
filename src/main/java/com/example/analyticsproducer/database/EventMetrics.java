package com.example.analyticsproducer.database;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "event_metrics")
public class EventMetrics {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private String date;

    @Column(name = "hour")
    private Integer hour;


    @Column(name = "total_order")
    private Integer totalOrder;


    @Column(name = "total_success_order")
    private Integer totalSuccessOrder;

    @Column(name = "total_failure_order")
    private Integer totalFailureOrder;

    @Column(name = "total_driver_accept")
    private Integer totalDriverAccept;


    @Column(name = "total_pickup")
    private Integer totalPickup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalSuccessOrder() {
        return totalSuccessOrder;
    }

    public void setTotalSuccessOrder(Integer totalSuccessOrder) {
        this.totalSuccessOrder = totalSuccessOrder;
    }

    public Integer getTotalFailureOrder() {
        return totalFailureOrder;
    }

    public void setTotalFailureOrder(Integer totalFailureOrder) {
        this.totalFailureOrder = totalFailureOrder;
    }

    public Integer getTotalDriverAccept() {
        return totalDriverAccept;
    }

    public void setTotalDriverAccept(Integer totalDriverAccept) {
        this.totalDriverAccept = totalDriverAccept;
    }

    public Integer getTotalPickup() {
        return totalPickup;
    }

    public void setTotalPickup(Integer totalPickup) {
        this.totalPickup = totalPickup;
    }
}
