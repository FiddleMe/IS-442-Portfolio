package com.BackendServices.Stock;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(String stockId, LocalDateTime dateTime) {
        return stockRepository.findByStockIdAndDateTime(stockId, dateTime);
    }

    public List<Stock> createStocks(List<Stock> stocks) {
        return stockRepository.saveAll(stocks);
    }

}
