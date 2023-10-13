package com.BackendServices.Stock;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
    // You can add custom queries or methods here if needed
    Stock findByStockIdAndDateTime(String stockId, LocalDateTime dateTime);
}
