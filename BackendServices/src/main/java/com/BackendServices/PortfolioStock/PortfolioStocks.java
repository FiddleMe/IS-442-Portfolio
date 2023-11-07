package com.BackendServices.PortfolioStock;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Portfolio_Stocks")
public class PortfolioStocks {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "Portfolio_StockID", length = 36, nullable = false)
  private String portfolioStockId;

  @Column(name = "Portfolio_ID", length = 36)
  private String portfolioId;

  @Column(name = "StockID", length = 36)
  private String stockId;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "Quantity")
  private Integer quantity;

  @Column(name = "Wallet")
  private Double wallet;

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

  public LocalDate getdate() {
    return date;
  }

  public void setdate(LocalDate date) {
    this.date = date;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getWallet() {
    return wallet;
  }

  public void setWallet(Double wallet) {
    this.wallet = wallet;
  }

}
