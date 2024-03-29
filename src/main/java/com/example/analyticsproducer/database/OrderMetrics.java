package com.example.analyticsproducer.database;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "order_metrics")
public class OrderMetrics {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "total_order")
    private Integer totalOrder;

    @Column(name = "total_restaurant")
    private Integer totalRestaurant;

    @Column(name = "total_driver")
    private Integer totalDriver;
    @Column(name = "total_customer")
    private Integer totalCustomer;

    @Column(name = "success_order")
    private Integer success_order;

    @Column(name = "failure_order")
    private Integer failure_order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Integer getTotalRestaurant() {
        return totalRestaurant;
    }

    public void setTotalRestaurant(Integer totalRestaurant) {
        this.totalRestaurant = totalRestaurant;
    }

    public Integer getTotalDriver() {
        return totalDriver;
    }

    public void setTotalDriver(Integer totalDriver) {
        this.totalDriver = totalDriver;
    }

    public Integer getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Integer totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Integer getSuccess_order() {
        return success_order;
    }

    public void setSuccess_order(Integer success_order) {
        this.success_order = success_order;
    }

    public Integer getFailure_order() {
        return failure_order;
    }

    public void setFailure_order(Integer failure_order) {
        this.failure_order = failure_order;
    }

    public OrderMetrics() {
    }

    @Override
    public String toString() {
        return "OrderMetrics{" +
                "id=" + id +
                ", date=" + date +
                ", hour=" + hour +
                ", totalOrder=" + totalOrder +
                ", totalRestaurant=" + totalRestaurant +
                ", totalDriver=" + totalDriver +
                ", totalCustomer=" + totalCustomer +
                ", success_order=" + success_order +
                ", failure_order=" + failure_order +
                '}';
    }
}
