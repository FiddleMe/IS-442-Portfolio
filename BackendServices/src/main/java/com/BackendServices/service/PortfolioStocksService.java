package com.BackendServices.service;

import org.springframework.stereotype.Service;

import com.BackendServices.entity.PortfolioStocks;
import com.BackendServices.repository.PortfolioStocksRepository;

import java.util.List;

@Service
public class PortfolioStocksService {
    private final PortfolioStocksRepository portfolioStocksRepository;

    public PortfolioStocksService(PortfolioStocksRepository portfolioStocksRepository) {
        this.portfolioStocksRepository = portfolioStocksRepository;
    }

    public List<PortfolioStocks> getAllPortfolioStocks() {
        return portfolioStocksRepository.findAll();
    }

    public PortfolioStocks createPortfolioStocks(PortfolioStocks portfolioStocks) {
        return portfolioStocksRepository.save(portfolioStocks);
    }

    // Implement other portfolio stocks-related methods as needed
}
