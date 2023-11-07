package com.BackendServices.PortfolioStock;

import org.springframework.stereotype.Service;

import com.BackendServices.Stock.Stock;
import com.BackendServices.Stock.StockService;
import com.BackendServices.PortfolioStock.exception.PortfolioStockException;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            if (portfolioStocksRepository.existsById(portfolioStockId)) {
                portfolioStocksRepository.deleteById(portfolioStockId);
                return true; // Deletion successful
            } else {
                return false; // Portfolio stock does not exist
            }
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
                response.put("stock", inputStock);
                return response;
            }
        }
        return response;
    }

    public Map<String, Object> getRangeStockPriceChange(String stockId, LocalDate startDate, LocalDate endDate) {
        Stock startStock = stockService.getStockById(stockId, startDate);
        Stock endStock = stockService.getStockById(stockId, endDate);
        Map<String, Object> response = new HashMap<>();

        if (startStock != null && endStock != null) {
            BigDecimal startPrice = startStock.getPrice();
            BigDecimal endPrice = endStock.getPrice();
            BigDecimal priceDifference = endPrice.subtract(startPrice);
            BigDecimal percentageDifference = priceDifference.divide(startPrice, 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));

            response.put("percentageDifference", percentageDifference);
            response.put("startStock", startStock);
            response.put("endStock", endStock);
        }

        return response;
    }

    // public BigDecimal getStockPriceChange(String stockId, LocalDate date){
    // return getLatestPrice(stockId).subtract(getPurchasePrice(stockId, date));
    // }
    public BigDecimal getLatestPrice(String stockId) {
        LocalDate latestDate = stockService.getLatestDate();
        Stock stock = stockService.getStockById(stockId, latestDate);
        System.out.println(latestDate + " " + stock);
        return stock.getPrice();
    }

    public BigDecimal getPurchasePrice(String stockId, LocalDate date) {
        Stock stock = stockService.getStockById(stockId, date);
        return stock.getPrice();
    }

    public String getName(String stockId) {
        LocalDate latestDate = stockService.getLatestDate();
        Stock stock = stockService.getStockById(stockId, latestDate);
        return stock.getName();
    }

    public String getGeographicalRegion(String stockId) {
        LocalDate latestDate = stockService.getLatestDate();
        Stock stock = stockService.getStockById(stockId, latestDate);
        return stock.getGeographicalRegion();
    }

    public String getIndustrySector(String stockId) {
        LocalDate latestDate = stockService.getLatestDate();
        Stock stock = stockService.getStockById(stockId, latestDate);
        return stock.getIndustrySector();
    }

    public LocalDate getLatestDate() {
        return stockService.getLatestDate();
    }

    // Implement other portfolio stocks-related methods as needed
}
