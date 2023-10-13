package com.BackendServices.PortfolioStock;

import org.springframework.stereotype.Service;

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

    public List<PortfolioStocks> getPortfolioStocksById(String portfolioId) {
        return portfolioStocksRepository.findAllByPortfolioId(portfolioId);
    }

    public PortfolioStocks createPortfolioStocks(PortfolioStocks portfolioStocks) {
        return portfolioStocksRepository.save(portfolioStocks);
    }

    public boolean deletePortfolioStockById(String portfolioStockId) {
        try {
            portfolioStocksRepository.deleteById(portfolioStockId);
            return true; // Deletion successful
        } catch (Exception e) {
            return false; // Deletion failed
        }
    }

    // Implement other portfolio stocks-related methods as needed
}
