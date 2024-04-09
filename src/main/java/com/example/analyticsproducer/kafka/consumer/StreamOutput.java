package com.example.analyticsproducer.kafka.consumer;

import com.example.analyticsproducer.dto.StreamOutputEvent;
import com.example.analyticsproducer.service.EventMetricsService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StreamOutput {


    private static final Logger LOGGER = LogManager.getLogger(StreamOutput.class);

    @Autowired
    private EventMetricsService eventMetricsService;

    @KafkaListener(topics = "event-counts", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        Gson gson = new Gson();
        StreamOutputEvent streamOutput = gson.fromJson(message, StreamOutputEvent.class);
        eventMetricsService.processEvent(streamOutput);
    }

}
