package com.BackendServices.Controller;

import com.BackendServices.entity.Stock;
import com.BackendServices.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable String stockId) {
        Stock stock = stockService.getStockById(stockId);
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
