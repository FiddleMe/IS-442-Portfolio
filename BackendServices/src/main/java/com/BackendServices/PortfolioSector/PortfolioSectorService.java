package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackendServices.PortfolioSector.exception.PortfolioSectorException;

import java.util.List;

@Service
public class PortfolioSectorService {
    private final PortfolioSectorRepository portfolioSectorRepository;

    @Autowired
    public PortfolioSectorService(PortfolioSectorRepository portfolioSectorRepository) {
        this.portfolioSectorRepository = portfolioSectorRepository;
    }

    public List<PortfolioSector> getAllPortfolioSectors() {
        List<PortfolioSector> portfolioSectors = portfolioSectorRepository.findAll();
        if (portfolioSectors.isEmpty()) {
            throw new PortfolioSectorException("No portfolio sectors found");
        }
        return portfolioSectors;
    }

    public PortfolioSector getPortfolioSectorById(String portfolioId, String sector) {
        return portfolioSectorRepository.findById(new PortfolioSector.PortfolioSectorKey(portfolioId, sector))
                .orElseThrow(() -> new PortfolioSectorException(
                        "Portfolio sector not found with portfolioId: " + portfolioId + " and sector: " + sector));
    }

    public List<PortfolioSector> getPortfolioSectorsByPortfolioId(String portfolioId) {
        List<PortfolioSector> portfolioSectors = portfolioSectorRepository.findByPortfolioId(portfolioId);
        if (portfolioSectors.isEmpty()) {
            throw new PortfolioSectorException("No portfolio sectors found with portfolioId: " + portfolioId);
        }
        return portfolioSectors;
    }

    public PortfolioSector createPortfolioSector(PortfolioSector portfolioSector) {
        if (portfolioSector == null) {
            throw new PortfolioSectorException("Portfolio sector cannot be null");
        }
        return portfolioSectorRepository.save(portfolioSector);
    }

    public List<PortfolioSector> createPortfolioSectors(List<PortfolioSector> portfolioSectors) {
        if (portfolioSectors == null || portfolioSectors.isEmpty()) {
            throw new PortfolioSectorException("Portfolio sectors cannot be null or empty");
        }
        return portfolioSectorRepository.saveAll(portfolioSectors);
    }

}