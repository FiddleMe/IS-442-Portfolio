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
            throw new PortfolioServiceException("Error retrieving all Portfolios", e);
        }
    }

    public PortfolioDTO getPortfolioById(String portfolioId) {
        try {
            Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);

            if (portfolio == null) {
                return null;
            }
            return PortfolioMapper.mapEntityToDTO(portfolio);
        } catch (Exception e) {
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
    
           
            return PortfolioMapper.mapEntityToDTO(savedPortfolio);
        } catch (Exception e) {
            throw new PortfolioServiceException("Error creating a new Portfolio", e);
        }
       
    }

    public PortfolioDTO updatePortfolio(String portfolioId, UpdatePortfolioDTO updatedPortfolioDTO) {
        try {
            if (!portfolioRepository.existsById(portfolioId)) {
                return null;
            }
            Portfolio existingPortfolio = portfolioRepository.findById(portfolioId).orElse(null);
            if (existingPortfolio == null) {
                return null;
            }
            if (updatedPortfolioDTO.getName() != null) {
                existingPortfolio.setName(updatedPortfolioDTO.getName());
            }
            if (updatedPortfolioDTO.getDescription() != null) {
                existingPortfolio.setDescription(updatedPortfolioDTO.getDescription());
            }
            if (updatedPortfolioDTO.getCapitalAmount() != null) {
                existingPortfolio.setCapitalAmount(updatedPortfolioDTO.getCapitalAmount());
            }
            Portfolio updatedPortfolio = portfolioRepository.save(existingPortfolio);
    
            return PortfolioMapper.mapEntityToDTO(updatedPortfolio);
        } catch (Exception e) {
            throw new PortfolioServiceException("Error updating Portfolio", e);
        }
    }
    
    

    public boolean deletePortfolio(String portfolioId) {
        if (portfolioRepository.existsById(portfolioId)) {
            portfolioRepository.deleteById(portfolioId);
            return true;
        }
        return false; 
    }
}
