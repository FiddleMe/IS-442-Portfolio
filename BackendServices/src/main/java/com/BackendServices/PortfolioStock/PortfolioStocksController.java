package com.BackendServices.PortfolioStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/portfoliostocks")
public class PortfolioStocksController {

    private final PortfolioStocksService portfolioStocksService;

    @Autowired
    public PortfolioStocksController(PortfolioStocksService portfolioStocksService) {
        this.portfolioStocksService = portfolioStocksService;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioStocks>> getAllPortfolioStocks() {
        List<PortfolioStocks> portfolioStocks = portfolioStocksService.getAllPortfolioStocks();
        return new ResponseEntity<>(portfolioStocks, HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<List<PortfolioStocks>> getPortfolioStocksById(@PathVariable String portfolioId) {
        List<PortfolioStocks> portfolioStocks = portfolioStocksService.getPortfolioStocksById(portfolioId);
        if (portfolioStocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(portfolioStocks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PortfolioStocks> createPortfolioStock(@RequestBody PortfolioStocks portfolioStock) {
        PortfolioStocks createdPortfolioStock = portfolioStocksService.createPortfolioStocks(portfolioStock);
        return new ResponseEntity<>(createdPortfolioStock, HttpStatus.CREATED);
    }

    @DeleteMapping("/{portfolioStockId}")
    public ResponseEntity<Void> deletePortfolioStockById(@PathVariable String portfolioStockId) {
        boolean deletionSuccessful = portfolioStocksService.deletePortfolioStockById(portfolioStockId);
        if (deletionSuccessful) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Failure
        }
    }

    // Add other endpoints as needed
}