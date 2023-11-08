package com.BackendServices.PortfolioSector;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.BackendServices.Portfolio.Portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PortfolioSector")
@IdClass(PortfolioSector.PortfolioSectorKey.class)
public class PortfolioSector {
  @Id
  @Column(name = "PortfolioID", length = 36, nullable = false)
  private String portfolioId;

  @Id
  @Column(name = "sector", length = 36, nullable = false)
  private String sector;

  @Column(name = "percentage", precision = 5, scale = 2)
  private BigDecimal percentage;
  
  @ManyToOne
  @JoinColumn(name = "PortfolioID", referencedColumnName = "PortfolioID", insertable = false, updatable = false)
  private Portfolio portfolio;
  


  public static class PortfolioSectorKey implements Serializable {
    private String portfolioId;
    private String sector;

    public PortfolioSectorKey() {
    }

    public PortfolioSectorKey(String portfolioId, String sector) {
        this.portfolioId = portfolioId;
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioSectorKey that = (PortfolioSectorKey) o;
        return Objects.equals(portfolioId, that.portfolioId) &&
                Objects.equals(sector, that.sector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, sector);
    }
  }

  // Getters and setters
  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }
}