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
        try {
            return portfolioStocksRepository.findAll();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get all PortfolioStocks", e);
        }
    }

    public List<PortfolioStocks> getPortfolioStocksById(String portfolioId) {
        try {
            return portfolioStocksRepository.findAllByPortfolioId(portfolioId);
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get PortfolioStocks by id", e);
        }
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
                throw new PortfolioStockException("PortfolioStock with id " + portfolioStockId + " does not exist");
            }
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to delete PortfolioStock", e);
        }
    }

    public Map<String, Object> getStockPriceChange(String stockId, LocalDate date) {
        try {
            Stock inputStock = stockService.getStockById(stockId, date);
            LocalDate latestDate = stockService.getLatestDate();
            Map<String, Object> response = new HashMap<>();

            if (inputStock == null) {
                throw new PortfolioStockException(
                        "Input stock not found for stockId: " + stockId + " and date: " + date);
            }

            Stock latestStock = stockService.getStockById(stockId, latestDate);

            if (latestStock == null) {
                throw new PortfolioStockException("Latest stock not found for stockId: " + stockId);
            }

            BigDecimal latestPrice = latestStock.getPrice();
            BigDecimal inputPrice = inputStock.getPrice();
            BigDecimal priceDifference = latestPrice.subtract(inputPrice);
            response.put("priceDifference", priceDifference);
            response.put("stock", inputStock);
            return response;
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get stock price change", e);
        }
    }

    public Map<String, Object> getRangeStockPriceChange(String stockId, LocalDate startDate, LocalDate endDate) {
        try {
            Stock startStock = stockService.getStockById(stockId, startDate);
            Stock endStock = stockService.getStockById(stockId, endDate);
            Map<String, Object> response = new HashMap<>();

            if (startStock == null || endStock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId);
            }

            BigDecimal startPrice = startStock.getPrice();
            BigDecimal endPrice = endStock.getPrice();
            BigDecimal priceDifference = endPrice.subtract(startPrice);
            BigDecimal percentageDifference = priceDifference.divide(startPrice, 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));

            response.put("percentageDifference", percentageDifference);
            response.put("startStock", startStock);
            response.put("endStock", endStock);
            return response;
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get range stock price change", e);
        }
    }

    public BigDecimal getLatestPrice(String stockId) {
        try {
            LocalDate latestDate = stockService.getLatestDate();
            Stock stock = stockService.getStockById(stockId, latestDate);

            if (stock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId);
            }

            return stock.getPrice();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get latest price", e);
        }
    }

    public BigDecimal getPurchasePrice(String stockId, LocalDate date) {
        try {
            Stock stock = stockService.getStockById(stockId, date);

            if (stock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId + " and date: " + date);
            }

            return stock.getPrice();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get purchase price", e);
        }
    }

    public String getName(String stockId) {
        try {
            LocalDate latestDate = stockService.getLatestDate();
            Stock stock = stockService.getStockById(stockId, latestDate);

            if (stock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId);
            }

            return stock.getName();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get name", e);
        }
    }

    public String getGeographicalRegion(String stockId) {
        try {
            LocalDate latestDate = stockService.getLatestDate();
            Stock stock = stockService.getStockById(stockId, latestDate);

            if (stock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId);
            }

            return stock.getGeographicalRegion();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get geographical region", e);
        }
    }

    public String getIndustrySector(String stockId) {
        try {
            LocalDate latestDate = stockService.getLatestDate();
            Stock stock = stockService.getStockById(stockId, latestDate);

            if (stock == null) {
                throw new PortfolioStockException("Stock not found for stockId: " + stockId);
            }

            return stock.getIndustrySector();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get industry sector", e);
        }
    }

    public LocalDate getLatestDate() {
        try {
            return stockService.getLatestDate();
        } catch (Exception e) {
            throw new PortfolioStockException("Failed to get latest date", e);
        }
    }

    // Implement other portfolio stocks-related methods as needed
}
