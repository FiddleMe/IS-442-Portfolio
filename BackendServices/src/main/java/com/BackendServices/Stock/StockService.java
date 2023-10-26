package com.BackendServices.Stock;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.BackendServices.Stock.dto.StockDTO;
import com.BackendServices.Stock.exception.StockServiceException;
import com.BackendServices.common.ApiResponse;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(String stockId, LocalDate date) {
        return stockRepository.findByStockIdAndDate(stockId, date);
    }

    public List<Stock> createStocks(Map<String, Object> timeSeriesDaily) {
        Map<String, String> metaData = (Map<String, String>) timeSeriesDaily.get("Meta Data");
        String symbol = (String) metaData.get("2. Symbol");

        // getting company overview data
        String apiKey = System.getenv("ALPHAVANTAGE_API_KEY"); //stored in environment variable windows command setx ALPHAVANTAGE_API_KEY=""
        String apiUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, String>>() {});
        Map<String, String> companyOverviewData = response.getBody();
        
        if (companyOverviewData!= null && companyOverviewData.isEmpty()   ){
            // return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), null, String.format("Could not get %s company overview data. Update unsuccessful.", symbol)));
            throw new StockServiceException("Error retrieving all companies");
        }
        //pass to mapping

        LocalDate latestDate = this.getLatestDate();
        List<Stock> stockDTOList = StockDTO.mapJsonToStockDTO(timeSeriesDaily, companyOverviewData, latestDate);
        return stockRepository.saveAll(stockDTOList);
    }
    

    public LocalDate getLatestDate() { //find latest date record in table
        return stockRepository.findLatestDate();
    }

    

}
