package com.BackendServices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BackendServices.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {
    // You can add custom queries or methods here if needed
}
