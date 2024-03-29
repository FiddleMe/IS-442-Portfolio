package com.BackendServices.Portfolio.dto;

import java.math.BigDecimal;

import com.BackendServices.Portfolio.Portfolio;

public class PortfolioDTO {
    private String portfolioId;
    private String name;
    private String description;
    private BigDecimal capitalAmount;
    private String userId;
    private BigDecimal balance;

    public PortfolioDTO() {
        
    }
    public PortfolioDTO(String name, String description, BigDecimal capitalAmount, String userId, BigDecimal balance) {
        this.name = name;
        this.description = description;
        this.capitalAmount = capitalAmount;
        this.userId = userId;   
        this.balance = balance;
    }
    public PortfolioDTO(String id) {
        
        this.portfolioId = id;
    }

    public PortfolioDTO(Portfolio portfolio) {
        this.name = portfolio.getName();
        this.description = portfolio.getDescription();
        this.capitalAmount = portfolio.getCapitalAmount();
        this.userId = portfolio.getUserId();
        this.balance = portfolio.getBalance();
    }

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
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public Portfolio convertToEntity() {
        Portfolio c = new Portfolio();
        c.setPortfolioId(portfolioId);
        c.setName(name); 
        c.setDescription(description); 
        c.setCapitalAmount(capitalAmount);
        c.setUserId(userId); 
        c.setBalance(balance);
        return c;
    }
   
}
