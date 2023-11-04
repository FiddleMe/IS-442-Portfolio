package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<PortfolioSector>> getAllPortfolioSectors() {
        List<PortfolioSector> portfolioSectors = portfolioSectorService.getAllPortfolioSectors();
        return new ResponseEntity<>(portfolioSectors, HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<List<PortfolioSector>> getPortfolioSectorsByPortfolioId(@PathVariable String portfolioId) {
        List<PortfolioSector> portfolioSectors = portfolioSectorService.getPortfolioSectorsByPortfolioId(portfolioId);
        return new ResponseEntity<>(portfolioSectors, HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}/{sector}")
    public ResponseEntity<PortfolioSector> getPortfolioSectorById(@PathVariable String portfolioId,
            @PathVariable String sector) {
        PortfolioSector portfolioSector = portfolioSectorService.getPortfolioSectorById(portfolioId, sector);
        return new ResponseEntity<>(portfolioSector, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PortfolioSector> createPortfolioSector(@RequestBody PortfolioSector portfolioSector) {
        PortfolioSector createdPortfolioSector = portfolioSectorService.createPortfolioSector(portfolioSector);
        return new ResponseEntity<>(createdPortfolioSector, HttpStatus.CREATED);
    }
}