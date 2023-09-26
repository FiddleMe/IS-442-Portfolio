package com.BackendServices.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Portfolio_Stocks")
public class PortfolioStocks {
    @Id
    @Column(name = "Portfolio_StockID", length = 36, nullable = false)
    private String portfolioStockId;

    @Column(name = "Portfolio_ID", length = 36)
    private String portfolioId;

    @Column(name = "StockID", length = 36)
    private String stockId;

    @Column(name = "DateTime")
    private LocalDateTime dateTime;

    @Column(name = "Quantity")
    private Integer quantity;

    // Getters and setters
}
