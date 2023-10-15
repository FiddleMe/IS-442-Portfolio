package com.BackendServices.Jobs;

import java.util.Map;

public class StockData {
    private StockMetaData stockMetaData;
    private StockTimeSeries stockTimeSeries;

    // Getters and setters

    public StockMetaData getMetaData() {
        return stockMetaData;
    }

    public void setMetaData(StockMetaData stockMetaData) {
        this.stockMetaData = stockMetaData;
    }

    public StockTimeSeries getTimeSeriesDaily() {
        return stockTimeSeries;
    }

    public void setTimeSeriesDaily(StockTimeSeries stockTimeSeries) {
        this.stockTimeSeries = stockTimeSeries;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stock Data:\n");
        sb.append("Metadata:\n");
        sb.append(stockMetaData.toString());
        sb.append("Time Series (Daily):\n");
        
        if (stockTimeSeries != null) {
            Map<String, StockInfo> timeSeries = stockTimeSeries.getTimeSeries();
            if (timeSeries != null) {
                for (Map.Entry<String, StockInfo> entry : timeSeries.entrySet()) {
                    String date = entry.getKey();
                    StockInfo stockInfo = entry.getValue();
                    sb.append("Date: ").append(date).append("\n");
                    sb.append("Open: ").append(stockInfo.getOpen()).append("\n");
                    sb.append("High: ").append(stockInfo.getHigh()).append("\n");
                    sb.append("Low: ").append(stockInfo.getLow()).append("\n");
                    sb.append("Close: ").append(stockInfo.getClose()).append("\n");
                    sb.append("Volume: ").append(stockInfo.getVolume()).append("\n");
                }
            }
        }

        return sb.toString();
    }
}
