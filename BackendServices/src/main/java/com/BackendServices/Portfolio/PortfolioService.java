package com.BackendServices.Portfolio;
import org.springframework.stereotype.Service;
import com.BackendServices.Portfolio.dto.*;
import java.util.*;
import com.BackendServices.Portfolio.exception.*;

import java.util.List;

@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    

    
    public List<PortfolioDTO> getAllPortfolios() {
        try {
            List<Portfolio> portfolios = portfolioRepository.findAll();
            List<PortfolioDTO> portfolioDTOs = new ArrayList<>();
    
            for (Portfolio portfolio : portfolios) {
                PortfolioDTO portfolioDTO = PortfolioMapper.mapEntityToDTO(portfolio);
                portfolioDTOs.add(portfolioDTO);
            }
    
            return portfolioDTOs;
        } catch (Exception e) {
            // Handle exceptions and log them
            throw new PortfolioServiceException("Error retrieving all Portfolios", e);
        }
    }

    public PortfolioDTO getPortfolioById(String portfolioId) {
        try {
            // Convert the portfolioId to a UUID if needed
           

            // Retrieve the Portfolio entity from the database
            Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);

            if (portfolio == null) {
                return null; // Handle not found case
            }

            // Map the entity to PortfolioDTO and return it
            return PortfolioMapper.mapEntityToDTO(portfolio);
        } catch (Exception e) {
            // Handle exceptions and log them
            throw new PortfolioServiceException("Error retrieving Portfolio by ID", e);
        }
    }


    public PortfolioDTO createPortfolio(PortfolioDTO PortfolioDTO) {
        try{
            Portfolio portfolio = new Portfolio();
            portfolio.setName(PortfolioDTO.getName());
            portfolio.setDescription(PortfolioDTO.getDescription());
            portfolio.setCapitalAmount(PortfolioDTO.getCapitalAmount());
            portfolio.setUserId(PortfolioDTO.getUserId());
            Portfolio savedPortfolio = portfolioRepository.save(portfolio);
    
            // Map the saved entity back to PortfolioDTO and return it
            return PortfolioMapper.mapEntityToDTO(savedPortfolio);
        } catch (Exception e) {
            // Handle exceptions and log them
            throw new PortfolioServiceException("Error creating a new Portfolio", e);
        }
       
    }

    public boolean deletePortfolio(String portfolioId) {
     
        if (portfolioRepository.existsById(portfolioId)) {
            portfolioRepository.deleteById(portfolioId);
            return true;
        }
        return false; // User not found
    }
    // Implement other portfolio-related methods as needed
}
