package com.example.analyticsproducer.service;

import com.example.analyticsproducer.dto.LineGraphResponse;
import com.example.analyticsproducer.dto.OrderMetricsResponse;
import com.example.analyticsproducer.dto.Series;
import com.example.analyticsproducer.repository.OrderMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderMetricsService {

    @Autowired
    private OrderMetricsRepository orderMetricsRepository;
    public OrderMetricsResponse getOrderMetrics(String startDate, String endDate) {
        List<Object[]> resultList = orderMetricsRepository.getTotalMetricsBetweenDates(startDate, endDate);
        OrderMetricsResponse orderMetricsResponse = new OrderMetricsResponse();
        if(ObjectUtils.isEmpty(resultList)) {
            orderMetricsResponse.setTotalOrder(0L);
            orderMetricsResponse.setTotalRestaurant(0L);
            orderMetricsResponse.setTotalDriver(0L);
            orderMetricsResponse.setTotalCustomer(0L);
            orderMetricsResponse.setSuccessOrder(0L);
            orderMetricsResponse.setFailureOrder(0L);
            return orderMetricsResponse;
        }
        Object[] result = resultList.get(0);
        Long totalOrder = extractLongValue(result[0]);
        Long totalRestaurant = extractLongValue(result[1]);
        Long totalDriver = extractLongValue(result[2]);
        Long totalCustomer = extractLongValue(result[3]);
        Long successOrder = extractLongValue(result[4]);
        Long failureOrder = extractLongValue(result[5]);

        orderMetricsResponse.setTotalOrder(totalOrder);
        orderMetricsResponse.setTotalRestaurant(totalRestaurant);
        orderMetricsResponse.setTotalDriver(totalDriver);
        orderMetricsResponse.setTotalCustomer(totalCustomer);
        orderMetricsResponse.setSuccessOrder(successOrder);
        orderMetricsResponse.setFailureOrder(failureOrder);
        return orderMetricsResponse;
    }

    private Long extractLongValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else {
            // Handle the case where the object is not a Number
            // For example, if the result contains null or non-numeric values
            return null; // or throw an exception, depending on your requirements
        }
    }

    public LineGraphResponse getLineGraph(String startDate, String endDate){
        List<Object[]> resultList = orderMetricsRepository.getLineGraph(startDate, endDate);
        // [
        //     [10,100,12,23,34],
        //     [11,33,34,33,33]
        // ]

        // {
        //     10: {
        //         totalOrder:123,
        //         totalDriver:123,
        //         totalCustomer:233,
        //         totalRestaurant:232
        //     }
        //     11: {
        //    ....
        // }


        Map<Long,OrderMetricsResponse> hourMetricsMap = new HashMap<>();
        for(Object[] result : resultList) {
            OrderMetricsResponse orderMetricsResponse = new OrderMetricsResponse();
            orderMetricsResponse.setTotalOrder(extractLongValue(result[1]));
            orderMetricsResponse.setTotalDriver(extractLongValue(result[2]));
            orderMetricsResponse.setTotalCustomer(extractLongValue(result[3]));
            orderMetricsResponse.setTotalRestaurant(extractLongValue(result[4]));
            hourMetricsMap.put(extractLongValue(result[0]),orderMetricsResponse);
        }
        Series successSeries = new Series("SUCCESS ORDER","#4caf50");
        Series driverSeries = new Series("DRIVER","#2196f3");
        Series customerSeries = new Series("CUSTOMER","#ffc107");
        Series failureSeries = new Series("FAILURE ORDER","#f44336");

        for(long i =0; i<=23; i=i+1){
            OrderMetricsResponse orderMetricsResponse = hourMetricsMap.get(i);
            if(ObjectUtils.isEmpty(orderMetricsResponse)){
                successSeries.getData().add(0L);
                driverSeries.getData().add(0L);
                customerSeries.getData().add(0L);
                failureSeries.getData().add(0L);
            } else {
                successSeries.getData().add(orderMetricsResponse.getTotalOrder());
                driverSeries.getData().add(orderMetricsResponse.getTotalDriver());
                customerSeries.getData().add(orderMetricsResponse.getTotalCustomer());
                failureSeries.getData().add(orderMetricsResponse.getTotalRestaurant());
            }
        }
            LineGraphResponse lineGraphResponse = new LineGraphResponse();
            List<Series> userSeries = new  ArrayList<>();
            userSeries.add(driverSeries);
            userSeries.add(customerSeries);
            List<Series> orderSeries = new  ArrayList<>();
            orderSeries.add(successSeries);
            orderSeries.add(failureSeries);
            lineGraphResponse.setUserSeries(userSeries);
            lineGraphResponse.setOrderSeries(orderSeries);
            return lineGraphResponse;
        }
        
    }
