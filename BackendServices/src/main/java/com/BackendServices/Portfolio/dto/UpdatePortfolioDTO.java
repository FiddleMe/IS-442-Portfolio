package com.BackendServices.Portfolio.dto;

import java.math.BigDecimal;
import java.util.Optional;
import com.BackendServices.Portfolio.Portfolio;


public class UpdatePortfolioDTO extends PortfolioDTO {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<BigDecimal> capitalAmount;

    public UpdatePortfolioDTO() {
        super();
        this.name = Optional.empty();
        this.description = Optional.empty();
        this.capitalAmount = Optional.empty();
    }

    public UpdatePortfolioDTO(Optional<String> name, Optional<String> description, Optional<BigDecimal> capitalAmount) {
        super();
        this.name = name;
        this.description = description;
        this.capitalAmount = capitalAmount;
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
        return portfolio;
    }
}
