package com.BackendServices.PortfolioSector;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioSectorRepository extends JpaRepository<PortfolioSector, PortfolioSector.PortfolioSectorKey> {
    // You can add custom queries or methods here if needed
    List<PortfolioSector> findByPortfolioId(String portfolioId);

}