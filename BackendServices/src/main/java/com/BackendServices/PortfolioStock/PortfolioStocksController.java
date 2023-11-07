package com.BackendServices.PortfolioStock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.BackendServices.Stock.Stock;
import com.BackendServices.Stock.StockRepository;
import com.BackendServices.Stock.StockService;
import com.BackendServices.common.ApiResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/portfoliostocks")
@CrossOrigin(origins = "http://localhost:3000")
public class PortfolioStocksController {

    private final PortfolioStocksService portfolioStocksService;

    @Autowired
    public PortfolioStocksController(PortfolioStocksService portfolioStocksService) {
        this.portfolioStocksService = portfolioStocksService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPortfolioStocks() {
        List<PortfolioStocks> portfolioStocks = portfolioStocksService.getAllPortfolioStocks();
        if (portfolioStocks.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND.value(),
                    "Failed to fetch portfolio stocks."));
        }
        return ResponseEntity
                .ok(new ApiResponse(HttpStatus.OK.value(), portfolioStocks, "All Portfolio Stocks Retrieved"));
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getPortfolioStocksById(@PathVariable String portfolioId) {
        List<PortfolioStocks> portfolioStocks = portfolioStocksService.getPortfolioStocksById(portfolioId);
        if (portfolioStocks.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NOT_FOUND.value(),
                    String.format("No portfolio found with ID (%s).", portfolioId)));
        }
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), portfolioStocks,
                String.format("Portfolio found with ID (%s).", portfolioId)));
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
                        stockid)));
    }

    @GetMapping("getRangeStockPriceChange/{stockId}/{startDate}/{endDate}")
    public ResponseEntity<?> getRangeStockPriceChange(@PathVariable String stockId, @PathVariable String startDate,
            @PathVariable String endDate) {
        LocalDate startDateValue = LocalDate.parse(startDate);
        LocalDate endDateValue = LocalDate.parse(endDate);
        Map<String, Object> response = portfolioStocksService.getRangeStockPriceChange(stockId, startDateValue,
                endDateValue);

        if (response.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(), response, String.format(
                    "Price retrieval for stock is unsuccessful.")));
        }

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), response,
                String.format(
                        "Price retrieval for stock (%s) is successful. Result is the %% change between the latest price and purchase price.",
                        stockId)));
    }

    @PostMapping
    public ResponseEntity<?> createPortfolioStock(@RequestBody PortfolioStocks portfolioStock) {
        PortfolioStocks createdPortfolioStock = portfolioStocksService.createPortfolioStocks(portfolioStock);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED.value(), createdPortfolioStock,
                "Posting of portfolio stock successful."));
    }

    @DeleteMapping("/{portfolioStockId}")
    public ResponseEntity<?> deletePortfolioStockById(@PathVariable String portfolioStockId) {
        boolean deletionSuccessful = portfolioStocksService.deletePortfolioStockById(portfolioStockId);
        if (deletionSuccessful) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.NO_CONTENT.value(),
                    String.format("Deletion for portfolio stock (%s) successful.", portfolioStockId)));
        } else {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    String.format("Deletion for portfolio stock (%s) unsuccessful or does not exist.",
                            portfolioStockId)));
        }
    }
}