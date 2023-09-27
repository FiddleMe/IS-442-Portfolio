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
    public String getPortfolioStockId() {
      return portfolioStockId;
    }

    public void setPortfolioStockId(String portfolioStockId) {
      this.portfolioStockId = portfolioStockId;
    }

    public String getPortfolioId() {
      return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
    }

    public String getStockId() {
      return stockId;
    }

    public void setStockId(String stockId) {
      this.stockId = stockId;
    }

    public LocalDateTime getDateTime() {
      return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
      this.dateTime = dateTime;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }

    
}
