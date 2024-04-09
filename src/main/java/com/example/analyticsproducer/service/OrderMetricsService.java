package com.example.analyticsproducer.service;

import com.example.analyticsproducer.dto.EchartResponce;
import com.example.analyticsproducer.dto.LineGraphResponse;
import com.example.analyticsproducer.dto.OrderMetricsResponse;
import com.example.analyticsproducer.dto.Series;
import com.example.analyticsproducer.repository.EventMetricsRepository;
import com.example.analyticsproducer.repository.OrderWaitTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderMetricsService {

    @Autowired
    private EventMetricsRepository eventMetricsRepository;
    @Autowired
    private OrderWaitTimeRepository orderWaitTimeRepository;
    public OrderMetricsResponse getOrderMetrics(String startDate, String endDate) {
        List<Object[]> resultList = eventMetricsRepository.getTotalMetricsBetweenDates(startDate, endDate);
        OrderMetricsResponse orderMetricsResponse = new OrderMetricsResponse();
        Double averageWaitTimeInSeconds = orderWaitTimeRepository.calculateAverageWaitTimeInSeconds(startDate, endDate);
        if(ObjectUtils.isEmpty(resultList)) {
            orderMetricsResponse.setTotalOrder(0L);
            orderMetricsResponse.setSuccessOrder(0L);
            orderMetricsResponse.setFailureOrder(0L);
            return orderMetricsResponse;
        }
        Object[] result = resultList.get(0);
        Long totalOrder = extractLongValue(result[0]);
        Long successOrder = extractLongValue(result[1]);
        Long failureOrder = extractLongValue(result[2]);
        String avgWaitTime =convertSecondsToTimeString(averageWaitTimeInSeconds);

        orderMetricsResponse.setTotalOrder(totalOrder);
        orderMetricsResponse.setSuccessOrder(successOrder);
        orderMetricsResponse.setFailureOrder(failureOrder);
        orderMetricsResponse.setAvgWaitTime(avgWaitTime);
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
        List<Object[]> resultList = eventMetricsRepository.getLineGraph(startDate, endDate);
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
            orderMetricsResponse.setSuccessOrder(extractLongValue(result[2]));
            orderMetricsResponse.setFailureOrder(extractLongValue(result[3]));
            hourMetricsMap.put(extractLongValue(result[0]),orderMetricsResponse);
        }
        Series successSeries = new Series("SUCCESS ORDER","#4caf50");
        Series totalOrderSeries = new Series("TOTAL ORDER","#2196f3");
        Series failureSeries = new Series("FAILURE ORDER","#f44336");

        for(long i =0; i<=23; i=i+1){
            OrderMetricsResponse orderMetricsResponse = hourMetricsMap.get(i);
            if(ObjectUtils.isEmpty(orderMetricsResponse)){
                successSeries.getData().add(0L);
                totalOrderSeries.getData().add(0L);
                failureSeries.getData().add(0L);
            } else {
                successSeries.getData().add(orderMetricsResponse.getSuccessOrder());
                totalOrderSeries.getData().add(orderMetricsResponse.getTotalOrder());
                failureSeries.getData().add(orderMetricsResponse.getFailureOrder());
            }
        }
            LineGraphResponse lineGraphResponse = new LineGraphResponse();
            List<Series> userSeries = new  ArrayList<>();
            userSeries.add(totalOrderSeries);
            userSeries.add(successSeries);
            List<Series> orderSeries = new  ArrayList<>();
            orderSeries.add(failureSeries);
            orderSeries.add(totalOrderSeries);
            lineGraphResponse.setUserSeries(userSeries);
            lineGraphResponse.setOrderSeries(orderSeries);
            return lineGraphResponse;
        }


    public EchartResponce getEchart(String s, String s1) {
        Series Series = new Series("CUSTOMER WAIT TIME","#fff");
        List<Long> data = new ArrayList<>();
        List<Series> seriesList = new ArrayList<>();
        data.add(orderWaitTimeRepository.countOrdersWithWaitTimeLessThan10Minutes(s,s1));
        data.add(orderWaitTimeRepository.countOrdersWithWaitTimeBetween10And30Minutes(s,s1));
        data.add(orderWaitTimeRepository.countOrdersWithWaitTimeBetween30And60Minutes(s,s1));
        data.add(orderWaitTimeRepository.countOrdersWithWaitTimeMoreThan60Minutes(s,s1));
        Series.setData(data);
        seriesList.add(Series);
        EchartResponce echartResponce = new EchartResponce();
        echartResponce.setSeries(seriesList);
        return echartResponce;
    }

    private String convertSecondsToTimeString(Double seconds) {
        if (seconds == null) {
            return "0s";
        }
        long hours = (long) (seconds / 3600);
        long minutes = (long) ((seconds % 3600) / 60);
        long remainingSeconds = (long) (seconds % 60);

        StringBuilder stringBuilder = new StringBuilder();

        if (hours > 0) {
            stringBuilder.append(hours).append(" hr ");
        }
        if (minutes > 0 || hours > 0) {
            stringBuilder.append(minutes).append(" min ");
        }
        stringBuilder.append(remainingSeconds).append(" s");

        return stringBuilder.toString();
    }
}
