package com.example.analyticsproducer.dto;

import java.io.Serializable;

public class StreamOutputEvent implements Serializable {

    private String eventType;
    private Integer count;
    private Integer hour;
    private String date;

    public StreamOutputEvent() {
    }

    @Override
    public String toString() {
        return "StreamOutput{" +
                "eventType='" + eventType + '\'' +
                ", count=" + count +
                ", hour=" + hour +
                ", date='" + date + '\'' +
                '}';
    }

    public StreamOutputEvent(String eventType, Integer count, Integer hour, String date) {
        this.eventType = eventType;
        this.count = count;
        this.hour = hour;
        this.date = date;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
