package com.BackendServices.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.BackendServices.Stock.dto.StockDTO;

import java.time.LocalDateTime;
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

    @GetMapping("/{stockId}/{dateTime}")
    public ResponseEntity<Stock> getStockById(@PathVariable String stockId, @PathVariable String dateTime) {
        LocalDateTime dateTimeValue = LocalDateTime.parse(dateTime);
        Stock stock = stockService.getStockById(stockId, dateTimeValue);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    // @PostMapping
    // public ResponseEntity<List<Stock>> createStocks(@RequestBody List<Stock>
    // stocks) {
    // List<Stock> createdStocks = stockService.createStocks(stocks);
    // return new ResponseEntity<>(createdStocks, HttpStatus.CREATED);
    // }

    @PostMapping
    public ResponseEntity<List<Stock>> createStocks(@RequestBody Map<String, Object> timeSeriesDaily) {
        Map<String, String> metaData = (Map<String, String>) timeSeriesDaily.get("Meta Data");
        String symbol = (String) metaData.get("2. Symbol");

        // getting company overview data
        String apiKey = System.getenv("ALPHAVANTAGE_API_KEY"); //stored in environment variable windows command setx ALPHAVANTAGE_API_KEY=""
        String apiUrl = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + symbol + "&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> companyOverviewData = restTemplate.getForObject(apiUrl, Map.class);

        //pass to mapping
        List<Stock> stockDTOList = StockDTO.mapJsonToStockDTO(timeSeriesDaily, companyOverviewData);
        List<Stock> createdStocks = stockService.createStocks(stockDTOList);

        return new ResponseEntity<>(createdStocks, HttpStatus.CREATED);
    }

}
