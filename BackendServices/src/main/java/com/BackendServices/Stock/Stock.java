package com.BackendServices.Stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stock")
@IdClass(Stock.StockKey.class)
public class Stock {
  @Id
  @Column(name = "StockID", length = 36, nullable = false)
  private String stockId;

  @Column(name = "DateTime", nullable = false)
  private LocalDateTime dateTime;

  @Column(name = "Name")
  private String name;

  @Column(name = "Price", precision = 10, scale = 4)
  private BigDecimal price;

  @Column(name = "GeographicalRegion")
  private String geographicalRegion;

  @Column(name = "IndustrySector")
  private String industrySector;

  public static class StockKey implements Serializable {
    private String stockId;
    private LocalDateTime dateTime;

    // Implement equals() and hashCode() methods (required for composite keys)
  }

  // Getters and setters
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getGeographicalRegion() {
    return geographicalRegion;
  }

  public void setGeographicalRegion(String geographicalRegion) {
    this.geographicalRegion = geographicalRegion;
  }

  public String getIndustrySector() {
    return industrySector;
  }

  public void setIndustrySector(String industrySector) {
    this.industrySector = industrySector;
  }

}
