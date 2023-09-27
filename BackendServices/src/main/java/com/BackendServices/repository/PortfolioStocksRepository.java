package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.entity.PortfolioStocks;

public interface PortfolioStocksRepository extends JpaRepository<PortfolioStocks, String> {
    // You can add custom queries or methods here if needed
}
