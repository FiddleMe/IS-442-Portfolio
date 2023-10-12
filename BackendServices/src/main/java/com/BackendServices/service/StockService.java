package com.BackendServices.service;
import org.springframework.stereotype.Service;

import com.BackendServices.entity.Stock;
import com.BackendServices.repository.StockRepository;

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

    public Stock getStockById(String stockId) {
        return stockRepository.findById(stockId).orElse(null);
    }

    public List<Stock> createStocks(List<Stock> stocks) {
        return stockRepository.saveAll(stocks);
    }

}
