package com.BackendServices.PortfolioStock;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.BackendServices.Portfolio.Portfolio;
import com.BackendServices.Stock.Stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Portfolio_Stocks")
public class PortfolioStocks {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "Portfolio_StockID", length = 36, nullable = false)
  private String portfolioStockId;

  @Column(name = "PortfolioID", length = 36)
  private String portfolioId;

  @Column(name = "StockID", length = 36)
  private String stockId;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "Quantity")
  private Integer quantity;


  @ManyToOne
  @JoinColumn(name = "PortfolioID", referencedColumnName = "PortfolioID", insertable = false, updatable = false)
  private Portfolio portfolio;

  @OneToMany(mappedBy = "portfolioStocks")
  private List<Stock> stock;


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

}
