package com.BackendServices.Stock;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, String> {
    // You can add custom queries or methods here if needed
    
    Stock findByStockIdAndDate(String stockId, LocalDate date);

    @Query(value = "SELECT max(s.date) FROM Stock s")
    LocalDate findLatestDate();
}
