package com.BackendServices.Jobs.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.BackendServices.Jobs.StockCronData;
import com.BackendServices.Jobs.StockCronService;


import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class StockCronConfiguration {
    private final StockCronService stockCronService;
    private int runCount = 0;

    public StockCronConfiguration(StockCronService stockCronService) {
        this.stockCronService = stockCronService;
    }


// ...

    @Scheduled(cron = "0/10 * * * * *")
    public void getStockData() {
        // Define the stock symbols you want to fetch
        String[] stockSymbols = {"AAPL", "MSFT", "GOOGL"};
        Map<String, String> stockDataMap = new HashMap<>();

        if (runCount < 1) { // Run for 1 time
            for (String symbol : stockSymbols) {
                String jsonString = stockCronService.getStockDataJson(symbol); // Replace with your method to fetch JSON response
                if (jsonString != null) { // Check if JSON response is not null
                    stockDataMap.put(symbol, jsonString);
                }
            }
        }


            // Process the stock data as needed
            // for (StockData stockData : stockDataList) {
            //     // StockMetaData metaData = stockData.getMetaData();
            //     StockTimeSeries timeSeriesDaily = stockData.getTimeSeriesDaily();

            //     if (timeSeriesDaily != null) {
            //         Map<String, StockInfo> timeSeries = timeSeriesDaily.getTimeSeries();
            //         for (Map.Entry<String, StockInfo> entry : timeSeries.entrySet()) {
            //             String date = entry.getKey();
            //             StockInfo dailyData = entry.getValue();
            //             System.out.println("Date: " + date);
            //             System.out.println("Open: " + dailyData.getOpen());
            //             System.out.println("High: " + dailyData.getHigh());
            //             System.out.println("Low: " + dailyData.getLow());
            //             System.out.println("Close: " + dailyData.getClose());
            //             System.out.println("Volume: " + dailyData.getVolume());
            //             // Process daily data as needed
            //         }
            //     }
            // }
            runCount++;
            for (Map.Entry<String, String> entry : stockDataMap.entrySet()) {
                String symbol = entry.getKey();
                String jsonString = entry.getValue();
                System.out.println("Stock Symbol: " + symbol);
                System.out.println("JSON Response: " + jsonString);
            }
    
    }
}


