package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    // You can add custom queries or methods here if needed
}
