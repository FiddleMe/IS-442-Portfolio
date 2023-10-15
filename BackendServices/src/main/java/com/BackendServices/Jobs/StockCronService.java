package com.BackendServices.Jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class StockCronService {
    @Value("${alphavantage.api.key}")
    private String apiKey;
    
  

    public StockCronData getStockData(String symbol) {
        System.out.println(apiKey);
        System.out.println(symbol);
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + symbol + "&interval=1min&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, StockCronData.class);
    }
}
