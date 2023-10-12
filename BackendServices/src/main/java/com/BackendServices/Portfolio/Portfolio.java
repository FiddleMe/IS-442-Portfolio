package com.BackendServices.Portfolio;


import java.math.BigDecimal;
import java.util.UUID;


import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "PortfolioID", length = 36, nullable = false)
    private String portfolioId;


    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Capital_Amount")
    private BigDecimal capitalAmount;

    @Column(name = "UserID", length = 36)
    private String userId;


    // Getters and setters
    public String getPortfolioId() {
      return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public BigDecimal getCapitalAmount() {
      return capitalAmount;
    }

    public void setCapitalAmount(BigDecimal capitalAmount) {
      this.capitalAmount = capitalAmount;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    
}
