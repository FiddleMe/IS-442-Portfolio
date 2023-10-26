package com.BackendServices.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.BackendServices.Stock.dto.StockDTO;
import com.BackendServices.common.ApiResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{stockId}/{date}")
    public ResponseEntity<Stock> getStockById(@PathVariable String stockId, @PathVariable String date) {
        LocalDate dateValue = LocalDate.parse(date);
        Stock stock = stockService.getStockById(stockId, dateValue);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }


    // @PostMapping
    // public ResponseEntity<?> createStocks(@RequestBody Map<String, Object> timeSeriesDaily) {
    //     Map<String, String> metaData = (Map<String, String>) timeSeriesDaily.get("Meta Data");
    //     String symbol = (String) metaData.get("2. Symbol");

    //     // getting company overview data
    //     String apiKey = System.getenv("ALPHAVANTAGE_API_KEY"); //stored in environment variable windows command setx ALPHAVANTAGE_API_KEY=""
    //     String apiUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
    //     RestTemplate restTemplate = new RestTemplate();
    //     Map<String, String> companyOverviewData = restTemplate.getForObject(apiUrl, Map.class);
    //     if (companyOverviewData.isEmpty()){
    //         return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), null, String.format("Could not get %s company overview data. Update unsuccessful.", symbol)));
    //     }
    //     //pass to mapping
    //     List<Stock> stockDTOList = StockDTO.mapJsonToStockDTO(timeSeriesDaily, companyOverviewData);
    //     stockService.createStocks(stockDTOList);

    //      return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED.value(), stockDTOList, String.format("Update for stock (%s) successful.", symbol)));
    // }
  
    @PostMapping
    public ResponseEntity<ApiResponse> createStocks(@RequestBody Map<String, Object> timeSeriesDaily) {
        List<Stock> createdStocks = stockService.createStocks(timeSeriesDaily);
        
        if (createdStocks.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), Collections.emptyList(), "No stocks created"));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.OK.value(), createdStocks, "Stocks created successfully"));
        }
    }

}
