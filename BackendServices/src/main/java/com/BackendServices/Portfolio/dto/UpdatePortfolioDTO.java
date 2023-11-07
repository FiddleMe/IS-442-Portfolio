package com.BackendServices.Portfolio.dto;

import java.math.BigDecimal;
import java.util.Optional;
import com.BackendServices.Portfolio.Portfolio;
import com.jayway.jsonpath.Option;


public class UpdatePortfolioDTO extends PortfolioDTO {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<BigDecimal> capitalAmount;
    private Optional<Double> wallet;

    public UpdatePortfolioDTO() {
        super();
        this.name = Optional.empty();
        this.description = Optional.empty();
        this.capitalAmount = Optional.empty();
        this.wallet = Optional.empty();
    }

    public UpdatePortfolioDTO(Optional<String> name, Optional<String> description, Optional<BigDecimal> capitalAmount, Optional<Double> wallet) {
        super();
        this.name = name;
        this.description = description;
        this.capitalAmount = capitalAmount;
        this.wallet = wallet;
    }
    
  
    public String getName() {
        return name.orElse(null); 
    }
    public void setName(Optional<String> name) {
        this.name = name;
    }

    public String getDescription() {
        return description.orElse(null);
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public BigDecimal getCapitalAmount() {
        return capitalAmount.orElse(null);
    }

    public void setCapitalAmount(Optional<BigDecimal> capitalAmount) {
        this.capitalAmount = capitalAmount;
    }


    public Double getWallet() {
        return wallet.orElse(null);
    }

    public void setWallet(Optional<Double> wallet) {
        this.wallet = wallet;
    }

    public Portfolio convertToEntity() {
        Portfolio portfolio = new Portfolio();
        if (getPortfolioId() != null) {
            portfolio.setPortfolioId(getPortfolioId());
        }
        if (getName() != null) {
            portfolio.setName(getName());
        }
        if (getDescription() != null) {
            portfolio.setDescription(getDescription());
        }
        if (getCapitalAmount() != null) {
            portfolio.setCapitalAmount(getCapitalAmount());
        }
        if (getWallet() != null) {
            portfolio.setWallet(getWallet());
        }
        return portfolio;
    }
}
