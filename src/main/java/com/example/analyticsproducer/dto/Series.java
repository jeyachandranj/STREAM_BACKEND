package com.example.analyticsproducer.dto;


import java.util.ArrayList;
import java.util.List;

public class Series {

    private String name;
    private String color;
    private List<Long> data;
    private Integer offsetY;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public String toString() {
        return "Series{" +
                "name='" + name + '\'' +
                ", data=" + data +
                ", offsetY=" + offsetY +
                '}';
    }

    public Series(String name,String color) {
        this.name = name;
        this.color = color;
        this.data =  new ArrayList<>();
        this.offsetY = 0;
    }
}
