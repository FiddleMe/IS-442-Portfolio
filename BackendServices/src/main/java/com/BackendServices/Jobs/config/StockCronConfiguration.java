package com.BackendServices.Jobs.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.BackendServices.Jobs.StockCronData;
import com.BackendServices.Jobs.StockCronService;
import com.BackendServices.Stock.StockService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class StockCronConfiguration {
    private final StockCronService stockCronService;
    private final StockService stockService;
    private int runCount = 0;

    public StockCronConfiguration(StockCronService stockCronService, StockService stockService) {
        this.stockCronService = stockCronService;
        this.stockService = stockService;
    }

    // ...

    public Map<String, Object> jsonStringToMap(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void getStockData() {
        // Define the stock symbols you want to fetch
        String[] stockSymbols = { "BABA", "ASML", "MNST", "SHEL"};
        if (runCount < 1) { // Run for 1 time
            for (String symbol : stockSymbols) {
                String jsonString = stockCronService.getStockDataJson(symbol); // Replace with your method to fetch JSON
                                                                               // response
                if (jsonString != null) { // Check if JSON response is not null
                    // call stock service
                    System.out.println(jsonString);
                    Map<String, Object> inputData = jsonStringToMap(jsonString);
                    Map<String, Object> filledData = fillInDataGaps(inputData);


                    stockService.createStocks(filledData);
                    System.out.println("done");
                   

                }
            }
            runCount++;
        }

        // Process the stock data as needed
        // for (StockData stockData : stockDataList) {
        // // StockMetaData metaData = stockData.getMetaData();
        // StockTimeSeries timeSeriesDaily = stockData.getTimeSeriesDaily();

        // if (timeSeriesDaily != null) {
        // Map<String, StockInfo> timeSeries = timeSeriesDaily.getTimeSeries();
        // for (Map.Entry<String, StockInfo> entry : timeSeries.entrySet()) {
        // String date = entry.getKey();
        // StockInfo dailyData = entry.getValue();
        // System.out.println("Date: " + date);
        // System.out.println("Open: " + dailyData.getOpen());
        // System.out.println("High: " + dailyData.getHigh());
        // System.out.println("Low: " + dailyData.getLow());
        // System.out.println("Close: " + dailyData.getClose());
        // System.out.println("Volume: " + dailyData.getVolume());
        // // Process daily data as needed
        // }
        // }
        // }
     
        // for (Map.Entry<String, String> entry : stockDataMap.entrySet()) {
        // String symbol = entry.getKey();
        // String jsonString = entry.getValue();
        // System.out.println("Stock Symbol: " + symbol);
        // System.out.println("JSON Response: " + jsonString);
        // }

    }

    public static Map<String, Object> fillInDataGaps(Map<String, Object> inputData) {
        Map<String, Map<String, String>> timeSeries = (Map<String, Map<String, String>>) inputData
                .get("Time Series (Daily)");
        SortedSet<String> sortedDates = new TreeSet<>(timeSeries.keySet());
        String previousDate = sortedDates.first();

        for (String date : sortedDates) {
            LocalDate previousLocalDate = LocalDate.parse(previousDate);
            LocalDate currentLocalDate = LocalDate.parse(date);

            while (previousLocalDate.plusDays(1).isBefore(currentLocalDate)) {
                previousLocalDate = previousLocalDate.plusDays(1);
                timeSeries.put(previousLocalDate.toString(), new HashMap<>(timeSeries.get(previousDate)));
            }

            previousDate = date;
        }

        inputData.put("Time Series (Daily)", timeSeries);
        return inputData;
    }
}
