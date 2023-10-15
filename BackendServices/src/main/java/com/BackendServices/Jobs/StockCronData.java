package com.BackendServices.Jobs;

import java.util.Map;

import com.BackendServices.Jobs.config.StockInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockCronData {
    @JsonProperty("Time Series (1min)")
    private Map<String, StockInfo> timeSeries;

    public Map<String, StockInfo> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(Map<String, StockInfo> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
