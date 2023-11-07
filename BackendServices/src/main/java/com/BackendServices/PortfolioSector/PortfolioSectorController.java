package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BackendServices.PortfolioSector.exception.PortfolioSectorException;

import java.util.List;

@RestController
@RequestMapping("api/portfoliosectors")
public class PortfolioSectorController {

    private final PortfolioSectorService portfolioSectorService;

    @Autowired
    public PortfolioSectorController(PortfolioSectorService portfolioSectorService) {
        this.portfolioSectorService = portfolioSectorService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPortfolioSectors() {
        try {
            List<PortfolioSector> portfolioSectors = portfolioSectorService.getAllPortfolioSectors();
            return new ResponseEntity<>(portfolioSectors, HttpStatus.OK);
        } catch (PortfolioSectorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getPortfolioSectorsByPortfolioId(@PathVariable String portfolioId) {
        try {
            List<PortfolioSector> portfolioSectors = portfolioSectorService.getPortfolioSectorsByPortfolioId(portfolioId);
            return new ResponseEntity<>(portfolioSectors, HttpStatus.OK);
        } catch (PortfolioSectorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{portfolioId}/{sector}")
    public ResponseEntity<?> getPortfolioSectorById(@PathVariable String portfolioId, @PathVariable String sector) {
        try {
            PortfolioSector portfolioSector = portfolioSectorService.getPortfolioSectorById(portfolioId, sector);
            return new ResponseEntity<>(portfolioSector, HttpStatus.OK);
        } catch (PortfolioSectorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPortfolioSector(@RequestBody PortfolioSector portfolioSector) {
        try {
            PortfolioSector createdPortfolioSector = portfolioSectorService.createPortfolioSector(portfolioSector);
            return new ResponseEntity<>(createdPortfolioSector, HttpStatus.CREATED);
        } catch (PortfolioSectorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}