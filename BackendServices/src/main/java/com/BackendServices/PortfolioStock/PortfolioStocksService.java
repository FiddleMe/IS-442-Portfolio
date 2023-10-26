package com.BackendServices.PortfolioStock;

import org.springframework.stereotype.Service;

import com.BackendServices.Stock.Stock;
import com.BackendServices.Stock.StockService;
import com.BackendServices.PortfolioStock.exception.PortfolioStockException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortfolioStocksService {
    private final PortfolioStocksRepository portfolioStocksRepository;
    private final StockService stockService;

    public PortfolioStocksService(PortfolioStocksRepository portfolioStocksRepository, StockService stockService) {
        this.portfolioStocksRepository = portfolioStocksRepository;
        this.stockService = stockService;
    }

    public List<PortfolioStocks> getAllPortfolioStocks() {
        return portfolioStocksRepository.findAll();
    }

    public List<PortfolioStocks> getPortfolioStocksById(String portfolioId) {
        return portfolioStocksRepository.findAllByPortfolioId(portfolioId);
    }

    public PortfolioStocks createPortfolioStocks(PortfolioStocks portfolioStocks) {
        try {
            return portfolioStocksRepository.save(portfolioStocks);
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to create PortfolioStocks", e);
        }
    }

    public boolean deletePortfolioStockById(String portfolioStockId) {
        try {
            portfolioStocksRepository.deleteById(portfolioStockId);
            return true; // Deletion successful
        } catch (Exception e) {
            return false; // Deletion failed
        }
    }

    public Map<String, Object> getStockPriceChange(String stockId, LocalDate date) {
        Stock inputStock = stockService.getStockById(stockId, date);
        LocalDate latestDate = stockService.getLatestDate();
        Map<String, Object> response = new HashMap<>();

        if (inputStock != null) {
            Stock latestStock = stockService.getStockById(stockId, latestDate);

            if (latestStock != null) {
                BigDecimal latestPrice = latestStock.getPrice();
                BigDecimal inputPrice = inputStock.getPrice();
                BigDecimal priceDifference = latestPrice.subtract(inputPrice);
                response.put("priceDifference", priceDifference);
                response.put("stockId", stockId);
                response.put("purchaseDate", date.toString());
                return response;
            }
        }
        return response;
    }

    // Implement other portfolio stocks-related methods as needed
}
