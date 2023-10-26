package com.BackendServices.Jobs;

import java.util.Map;

public class StockCronData {
    private MetaData metaData;
    private Map<String, DailyData> timeSeriesDaily;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, DailyData> getTimeSeriesDaily() {
        return timeSeriesDaily;
    }

    public void setTimeSeriesDaily(Map<String, DailyData> timeSeriesDaily) {
        this.timeSeriesDaily = timeSeriesDaily;
    }
}

class MetaData {
    private String information;
    private String symbol;
    private String lastRefreshed;
    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(String lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(String outputSize) {
        this.outputSize = outputSize;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    private String outputSize;
    private String timeZone;

    // Getters and setters for the MetaData fields
    // ...

    @Override
    public String toString() {
        return "MetaData{" +
                "information='" + information + '\'' +
                ", symbol='" + symbol + '\'' +
                ", lastRefreshed='" + lastRefreshed + '\'' +
                ", outputSize='" + outputSize + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}

class DailyData {
    private String open;
    private String high;
    private String low;
    private String close;
    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getAdjustedClose() {
        return adjustedClose;
    }

    public void setAdjustedClose(String adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(String dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public String getSplitCoefficient() {
        return splitCoefficient;
    }

    public void setSplitCoefficient(String splitCoefficient) {
        this.splitCoefficient = splitCoefficient;
    }

    private String adjustedClose;
    private String volume;
    private String dividendAmount;
    private String splitCoefficient;

    // Getters and setters for the DailyData fields
    // ...

    @Override
    public String toString() {
        return "DailyData{" +
                "open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", adjustedClose='" + adjustedClose + '\'' +
                ", volume='" + volume + '\'' +
                ", dividendAmount='" + dividendAmount + '\'' +
                ", splitCoefficient='" + splitCoefficient + '\'' +
                '}';
    }
}
