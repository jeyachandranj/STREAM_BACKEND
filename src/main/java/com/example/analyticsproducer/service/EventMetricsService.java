package com.example.analyticsproducer.service;

import com.example.analyticsproducer.database.EventMetrics;
import com.example.analyticsproducer.dto.StreamOutputEvent;
import com.example.analyticsproducer.repository.EventMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventMetricsService {

    @Autowired
    private EventMetricsRepository eventMetricsRepository;

    public void processEvent(StreamOutputEvent eventPayload) {
        EventMetrics existingMetrics = eventMetricsRepository.findByDateAndHour(eventPayload.getDate(), eventPayload.getHour());
        if (existingMetrics == null) {
            // Create a new EventMetrics entity
            EventMetrics newMetrics = new EventMetrics();
            newMetrics.setDate(eventPayload.getDate());
            newMetrics.setHour(eventPayload.getHour());
            newMetrics.setTotalSuccessOrder(0);
            newMetrics.setTotalFailureOrder(0);
            newMetrics.setTotalDriverAccept(0);
            newMetrics.setTotalPickup(0);
            newMetrics.setTotalOrder(0);
            updateMetrics(newMetrics, eventPayload);
            eventMetricsRepository.save(newMetrics);
        } else {
            // Update the existing EventMetrics entity
            updateMetrics(existingMetrics, eventPayload);
            eventMetricsRepository.save(existingMetrics);
        }
    }

    private void updateMetrics(EventMetrics metrics, StreamOutputEvent eventPayload) {
        switch (eventPayload.getEventType()) {
            case "CUSTOMER_ORDER_FOOD":
                metrics.setTotalOrder(metrics.getTotalOrder() + eventPayload.getCount());
                break;
            case "CUSTOMER_REJECT":
                metrics.setTotalFailureOrder(metrics.getTotalFailureOrder() + eventPayload.getCount());
                break;
            case "DRIVER_ACCEPT":
                metrics.setTotalDriverAccept(metrics.getTotalDriverAccept() + eventPayload.getCount());
                break;
            case "PICK_UP_FROM_RESTAURANT":
                metrics.setTotalPickup(metrics.getTotalPickup() + eventPayload.getCount());
                break;
            case "DROP_UP_TO_CUSTOMER":
                metrics.setTotalSuccessOrder(metrics.getTotalSuccessOrder() + eventPayload.getCount());
                break;
            default:
                break;
        }
    }
}
