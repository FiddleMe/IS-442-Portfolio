package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioSectorService {
    private final PortfolioSectorRepository portfolioSectorRepository;

    @Autowired
    public PortfolioSectorService(PortfolioSectorRepository portfolioSectorRepository) {
        this.portfolioSectorRepository = portfolioSectorRepository;
    }

    public List<PortfolioSector> getAllPortfolioSectors() {
        return portfolioSectorRepository.findAll();
    }

    public PortfolioSector getPortfolioSectorById(String portfolioId, String sector) {
        return portfolioSectorRepository.findById(new PortfolioSector.PortfolioSectorKey(portfolioId, sector)).orElse(null);
    }

    public List<PortfolioSector> getPortfolioSectorsByPortfolioId(String portfolioId) {
        return portfolioSectorRepository.findByPortfolioId(portfolioId);
    }

    public PortfolioSector createPortfolioSector(PortfolioSector portfolioSector) {
        return portfolioSectorRepository.save(portfolioSector);
    }

    public List<PortfolioSector> createPortfolioSectors(List<PortfolioSector> portfolioSectors) {
        return portfolioSectorRepository.saveAll(portfolioSectors);
    }

}