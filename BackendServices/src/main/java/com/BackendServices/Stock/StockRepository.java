package com.BackendServices.Stock;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
    // You can add custom queries or methods here if needed
    Stock findByStockIdAndDate(String stockId, LocalDate date);
}
