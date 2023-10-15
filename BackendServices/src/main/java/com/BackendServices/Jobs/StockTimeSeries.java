package com.BackendServices.Jobs;

import java.util.Map;



public class StockTimeSeries {
    private Map<String, StockInfo> timeSeries;


    public Map<String, StockInfo> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, StockInfo> timeSeries) {
        this.timeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "StockCronData{" +
                "timeSeries=" + timeSeries +
                '}';
    }
}
