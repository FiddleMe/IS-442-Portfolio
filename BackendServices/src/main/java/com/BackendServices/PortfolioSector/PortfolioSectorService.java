package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BackendServices.PortfolioSector.exception.PortfolioSectorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PortfolioSectorService {
    private final PortfolioSectorRepository portfolioSectorRepository;

    @Autowired
    public PortfolioSectorService(PortfolioSectorRepository portfolioSectorRepository) {
        this.portfolioSectorRepository = portfolioSectorRepository;
    }

    public List<PortfolioSector> getAllPortfolioSectors() throws PortfolioSectorException {
        List<PortfolioSector> portfolioSectors = portfolioSectorRepository.findAll();
        if (portfolioSectors.isEmpty()) {
            throw new PortfolioSectorException("No portfolio sectors found");
        }
        return portfolioSectors;
    }

    public PortfolioSector getPortfolioSectorById(String portfolioId, String sector) throws PortfolioSectorException {
        return portfolioSectorRepository.findById(new PortfolioSector.PortfolioSectorKey(portfolioId, sector))
                .orElseThrow(() -> new PortfolioSectorException(
                        "Portfolio sector not found with portfolioId: " + portfolioId + " and sector: " + sector));
    }

    public List<PortfolioSector> getPortfolioSectorsByPortfolioId(String portfolioId) throws PortfolioSectorException {
        List<PortfolioSector> portfolioSectors = portfolioSectorRepository.findByPortfolioId(portfolioId);
        if (portfolioSectors.isEmpty()) {
            throw new PortfolioSectorException("No portfolio sectors found with portfolioId: " + portfolioId);
        }
        return portfolioSectors;
    }

    public PortfolioSector createPortfolioSector(PortfolioSector portfolioSector) throws PortfolioSectorException {
        if (portfolioSector == null) {
            throw new PortfolioSectorException("Portfolio sector cannot be null");
        }
        return portfolioSectorRepository.save(portfolioSector);
    }

    // public List<PortfolioSector> createPortfolioSectors(List<PortfolioSector>
    // portfolioSectors)
    // throws PortfolioSectorException {
    // if (portfolioSectors == null || portfolioSectors.isEmpty()) {
    // throw new PortfolioSectorException("Portfolio sectors cannot be null or
    // empty");
    // }
    // return portfolioSectorRepository.saveAll(portfolioSectors);
    // }

    public List<PortfolioSector> createPortfolioSectors(String portfolioId, List<Map<String, Object>> stockRequests)
        throws PortfolioSectorException {
        if (stockRequests == null || stockRequests.isEmpty()) {
        throw new PortfolioSectorException("Stock requests cannot be null or empty");
        }

        BigDecimal totalPrice = stockRequests.stream()
            .map(stock -> new BigDecimal(stock.get("price").toString())
                .multiply(BigDecimal.valueOf((Integer) stock.get("quantity"))))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPrice.compareTo(BigDecimal.ZERO) == 0) {
        throw new PortfolioSectorException("Total price cannot be zero");
        }

        Map<String, List<Map<String, Object>>> stocksBySector = stockRequests.stream()
            .collect(Collectors.groupingBy(stock -> stock.get("industrySector").toString()));

        List<PortfolioSector> portfolioSectors = new ArrayList<>();

        for (Map.Entry<String, List<Map<String, Object>>> entry : stocksBySector.entrySet()) {
        BigDecimal sectorPrice = entry.getValue().stream()
            .map(stock -> new BigDecimal(stock.get("price").toString())
                .multiply(BigDecimal.valueOf((Integer) stock.get("quantity"))))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sectorPercentage;
        try {
            sectorPercentage = sectorPrice.divide(totalPrice, 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
        } catch (ArithmeticException e) {
            throw new PortfolioSectorException("Error while calculating sector percentage", e);
        }

        PortfolioSector portfolioSector = new PortfolioSector();
        portfolioSector.setPortfolioId(portfolioId);
        portfolioSector.setSector(entry.getKey());
        portfolioSector.setPercentage(sectorPercentage);

        portfolioSectors.add(portfolioSector);
        }

        return portfolioSectorRepository.saveAll(portfolioSectors);
    }

}