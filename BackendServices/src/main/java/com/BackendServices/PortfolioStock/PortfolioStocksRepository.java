package com.BackendServices.PortfolioStock;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PortfolioStocksRepository extends JpaRepository<PortfolioStocks, String> {
    // You can add custom queries or methods here if needed
    List<PortfolioStocks> findAllByPortfolioId(String portfolioId);
}
