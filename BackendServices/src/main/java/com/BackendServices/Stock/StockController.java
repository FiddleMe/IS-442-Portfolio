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
@CrossOrigin(origins = "http://localhost:3000")

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
    public ResponseEntity<?> getStockById(@PathVariable String stockId, @PathVariable String date) {
        LocalDate dateValue = LocalDate.parse(date);
        Stock stock = stockService.getStockById(stockId, dateValue);
        if (stock == null) {
            return ResponseEntity
                    .ok(new ApiResponse(HttpStatus.NO_CONTENT.value(),
                            String.format("No stocks found with ID (%s) and Date (%s)", stockId, dateValue)));
        }
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), stock,
                String.format("Stock found with ID (%s) and Date (%s)", stockId, dateValue)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStocks(@RequestBody Map<String, Object> timeSeriesDaily) {
        List<Stock> createdStocks = stockService.createStocks(timeSeriesDaily);

        if (createdStocks.isEmpty()) {
            return ResponseEntity
                    .ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), Collections.emptyList(), "No stocks created"));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(HttpStatus.OK.value(), createdStocks, "Stocks created successfully"));
        }
    }

    

}
