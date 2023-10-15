package com.BackendServices.PortfolioStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.Stock.Stock;
import com.BackendServices.Stock.StockRepository;
import com.BackendServices.Stock.StockService;
import com.BackendServices.common.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("getStockPriceChange/{stockid}/{date}")
    public ResponseEntity<?> getStockPriceChange(@PathVariable String stockid, @PathVariable String date) {
        LocalDate dateValue = LocalDate.parse(date);
        Map<String, Object> response = portfolioStocksService.getStockPriceChange(stockid, dateValue);
        if (response.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), response, String.format(
                    "Price retrival for stock is unsuccessful.")));
        }
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), response,
                String.format(
                        "Price retrival for stock (%s) is successful.Result is latest price subtract purchase price.",
                        response.get("stockId"))));
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