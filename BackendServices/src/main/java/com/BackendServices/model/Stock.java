package com.BackendServices.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @Column(name = "StockID", length = 36, nullable = false)
    private String stockId;

    @Column(name = "DateTime", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price", precision = 10, scale = 4)
    private BigDecimal price;

    @Column(name = "GeographicalRegion")
    private String geographicalRegion;

    @Column(name = "IndustrySector")
    private String industrySector;

    // Getters and setters
}
