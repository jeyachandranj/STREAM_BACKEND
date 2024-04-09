package com.example.analyticsproducer.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.analyticsproducer.database.OrderWaitTime;
@Repository
public interface OrderWaitTimeRepository extends JpaRepository<OrderWaitTime, Long> {
    @Query(value = "SELECT AVG(customer_wait_time) FROM order_wait_times WHERE Date BETWEEN :startTime AND :endTime", nativeQuery = true)
    Double calculateAverageWaitTimeInSeconds(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT COUNT(*) FROM order_wait_times WHERE customer_wait_time < 600 AND Date BETWEEN :startTime AND :endTime", nativeQuery = true)
    long countOrdersWithWaitTimeLessThan10Minutes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT COUNT(*) FROM order_wait_times WHERE customer_wait_time >= 600 AND customer_wait_time < 1800 AND Date BETWEEN :startTime AND :endTime", nativeQuery = true)
    long countOrdersWithWaitTimeBetween10And30Minutes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT COUNT(*) FROM order_wait_times WHERE customer_wait_time >= 1800 AND customer_wait_time < 3600 AND Date BETWEEN :startTime AND :endTime", nativeQuery = true)
    long countOrdersWithWaitTimeBetween30And60Minutes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT COUNT(*) FROM order_wait_times WHERE customer_wait_time >= 3600 AND Date BETWEEN :startTime AND :endTime", nativeQuery = true)
    long countOrdersWithWaitTimeMoreThan60Minutes(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
