package com.example.analyticsproducer.repository;

import com.example.analyticsproducer.database.EventMetrics;
import com.example.analyticsproducer.database.OrderMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventMetricsRepository extends JpaRepository<EventMetrics, Integer> {
    EventMetrics findByDateAndHour(String date, Integer hour);

    @Query(value = "SELECT SUM(total_order) as totalOrder,  SUM(total_success_order) as successOrder , SUM(total_failure_order) as failureOrder FROM event_metrics WHERE date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Object[]> getTotalMetricsBetweenDates(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT hour, AVG(total_order) AS totalOrder, AVG(total_success_order) AS successOrder, AVG(total_failure_order) AS failureOrder FROM event_metrics WHERE date BETWEEN :startDate AND :endDate GROUP BY hour", nativeQuery = true)
    List<Object[]> getLineGraph(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
