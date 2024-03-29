package com.example.analyticsproducer.repository;

import com.example.analyticsproducer.database.OrderMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface OrderMetricsRepository extends JpaRepository<OrderMetrics, Integer> {
    @Query(value = "SELECT SUM(total_order) as totalOrder, SUM(total_restaurant) as totalRestaurant, SUM(total_driver) as totalDriver, SUM(total_customer) as totalCustomer , SUM(success_order) as successOrder , SUM(failure_order) as failureOrder FROM order_metrics WHERE date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Object[]> getTotalMetricsBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT hour, AVG(success_order) AS average_success, AVG(total_driver) AS average_total_driver, AVG(total_customer) AS average_total_customer, AVG(failure_order) AS average_failure FROM order_metrics WHERE date BETWEEN :startDate AND :endDate GROUP BY hour", nativeQuery = true)
    List<Object[]> getLineGraph(@Param("startDate") String startDate, @Param("endDate") String endDate);

}

