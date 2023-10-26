package com.BackendServices.Stock;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends JpaRepository<Stock, String> {
    // You can add custom queries or methods here if needed

    Stock findByStockIdAndDate(String stockId, LocalDate date);

    @Query(value = "SELECT max(s.date) FROM Stock s")
    LocalDate findLatestDate();

    @Query(value = "SELECT max(s.date) FROM Stock s WHERE s.stockId = :stockId")
    LocalDate findLatestDateByStockSymbol(@Param("stockId") String stockId);
}
