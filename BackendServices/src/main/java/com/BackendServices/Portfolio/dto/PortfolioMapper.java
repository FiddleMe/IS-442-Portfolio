package com.BackendServices.Portfolio.dto;

import com.BackendServices.Portfolio.Portfolio;


public class PortfolioMapper {

    public static PortfolioDTO mapEntityToDTO(Portfolio portfolio) {
        PortfolioDTO dto = new PortfolioDTO();
        dto.setPortfolioId(portfolio.getPortfolioId()); 
        dto.setName(portfolio.getName());
        dto.setDescription(portfolio.getDescription());
        dto.setCapitalAmount(portfolio.getCapitalAmount());
        dto.setUserId(portfolio.getUserId());
        dto.setWallet(portfolio.getWallet());
        return dto;
    }
  
}


