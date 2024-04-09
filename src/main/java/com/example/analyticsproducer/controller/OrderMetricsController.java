package com.example.analyticsproducer.controller;

import com.example.analyticsproducer.dto.EchartResponce;
import com.example.analyticsproducer.dto.LineGraphResponse;
import com.example.analyticsproducer.dto.OrderMetricsResponse;
import com.example.analyticsproducer.service.OrderMetricsService;
import com.example.analyticsproducer.util.DateUtil;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
@Controller
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderMetricsController {

    @Autowired
    private OrderMetricsService orderMetricsService;
    @RequestMapping(value="/order-metrics",method= RequestMethod.GET)
    public ResponseEntity<OrderMetricsResponse> getOrderMatrics(@RequestParam("startDate") Long startDate,
                                                             @RequestParam("endDate") Long endDate) {
        // Parse the date strings into java.util.Date objects

        OrderMetricsResponse response = orderMetricsService.getOrderMetrics(
                DateUtil.convertToDateFormate(startDate),DateUtil.convertToDateFormate(endDate));
        return new ResponseEntity<OrderMetricsResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/order-metrics/line-graph",method= RequestMethod.GET)
    public ResponseEntity<LineGraphResponse> getLineGraph(@RequestParam("startDate") Long startDate,
                                                          @RequestParam("endDate") Long endDate) {
        // Parse the date strings into java.util.Date objects

        LineGraphResponse response = orderMetricsService.getLineGraph(
                DateUtil.convertToDateFormate(startDate),DateUtil.convertToDateFormate(endDate));
        return new ResponseEntity<LineGraphResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/order-metrics/e-chart",method= RequestMethod.GET)
    public ResponseEntity<EchartResponce> getEchart(@RequestParam("startDate") Long startDate,
                                                          @RequestParam("endDate") Long endDate) {
        // Parse the date strings into java.util.Date objects

        EchartResponce response = orderMetricsService.getEchart(
                DateUtil.convertToDateFormate(startDate),DateUtil.convertToDateFormate(endDate));
        return new ResponseEntity<EchartResponce>(response, HttpStatus.OK);
    }
}
