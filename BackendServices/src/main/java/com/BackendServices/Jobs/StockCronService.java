package com.BackendServices.Jobs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class StockCronService {
    @Value("${alphavantage.api.key}")
    private String apiKey;

    public StockData getStockData(String symbol) {
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + symbol + "&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<StockData> response = restTemplate.getForEntity(apiUrl, StockData.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                StockData stockData = response.getBody();
                // Log or debug the response
                System.out.println("API Response: " + stockData);
                return stockData;
            } else {
                // Log the HTTP status code if it's not OK
                System.err.println("API Response Error - HTTP Status Code: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Handle client errors (4xx) here, if necessary
            System.err.println("API Client Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions or errors here
            System.err.println("API Error: " + e.getMessage());
        }

        // Return null or throw an exception, depending on your error handling strategy
        return null;
    }
}
