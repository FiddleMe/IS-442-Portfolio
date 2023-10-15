package com.BackendServices.Jobs.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.BackendServices.Jobs.StockCronData;
import com.BackendServices.Jobs.StockCronService;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class StockCronConfiguration {
    private final StockCronService stockCronService;
    private int runCount = 0;

    public StockCronConfiguration(StockCronService stockCronService) {
        this.stockCronService = stockCronService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs at 12 AM every day
    public void getStockData() {
        String[] stockSymbols = {"AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "FB", "JPM", "V", "JNJ", "BRK.B", "WMT", "BAC", "PG", "INTC", "VZ", "DIS", "T", "CSCO", "HD", "PFE"};
        List<StockCronData> stockDataList = new ArrayList<>();
        if (runCount < 3) { // Run for 3 times            
            for (String symbol : stockSymbols) {
                StockCronData stockData = stockCronService.getStockData(symbol);
                stockDataList.add(stockData);
            }
        // Process the stock data as needed
        for (StockCronData stockData : stockDataList) {
            // Process each stock's data
            System.out.println(stockData.getTimeSeries());
        }

        runCount++;
    }
    }
}

