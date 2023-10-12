package com.BackendServices.Portfolio;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    
}
