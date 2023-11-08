package com.BackendServices.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.BackendServices.Stock.dto.StockDTO;
import com.BackendServices.Stock.exception.StockServiceException;
import com.BackendServices.common.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

@RequestMapping("api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<?> getAllStocks() {
        try {
            List<Stock> stocks = stockService.getAllStocks();
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        } catch (StockServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @GetMapping("/{stockId}/{date}")
    public ResponseEntity<?> getStockById(@PathVariable String stockId, @PathVariable String date) {
        try {
            LocalDate dateValue = LocalDate.parse(date);
            Stock stock = stockService.getStockById(stockId, dateValue);
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), stock,
                    String.format("Stock found with ID (%s) and Date (%s)", stockId, dateValue)));
        } catch (StockServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createStocks(@RequestBody Map<String, Object> timeSeriesDaily) {
        try {
            List<Stock> createdStocks = stockService.createStocks(timeSeriesDaily);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(HttpStatus.OK.value(), createdStocks, "Stocks created successfully"));
        } catch (StockServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        }
    }

    

}
