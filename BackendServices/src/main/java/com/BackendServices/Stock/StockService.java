package com.BackendServices.Stock;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Stock getStockById(String stockId, LocalDate date) {
        return stockRepository.findByStockIdAndDate(stockId, date);
    }

    public List<Stock> createStocks(List<Stock> stocks) {
        return stockRepository.saveAll(stocks);
    }

}
