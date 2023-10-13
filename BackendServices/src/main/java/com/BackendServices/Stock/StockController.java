package com.BackendServices.Stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    
    @PostMapping
    public ResponseEntity<List<Stock>> createStocks(@RequestBody List<Stock> stocks) {
        List<Stock> createdStocks = stockService.createStocks(stocks);
        return new ResponseEntity<>(createdStocks, HttpStatus.CREATED);
    }
    
}
