package com.BackendServices.PortfolioSector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BackendServices.PortfolioSector.exception.PortfolioSectorException;
import com.BackendServices.common.ApiResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            if (!portfolioSectors.isEmpty()) {
                return ResponseEntity
                        .ok(new ApiResponse(HttpStatus.OK.value(), portfolioSectors, "Portfolio sectors found"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "No portfolio sectors found"));
            }
        } catch (PortfolioSectorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, e.getMessage()));
        }
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getPortfolioSectorsByPortfolioId(@PathVariable String portfolioId) {
        try {
            List<PortfolioSector> portfolioSectors = portfolioSectorService
                    .getPortfolioSectorsByPortfolioId(portfolioId);
            if (!portfolioSectors.isEmpty()) {
                return ResponseEntity
                        .ok(new ApiResponse(HttpStatus.OK.value(), portfolioSectors, "Portfolio sectors found"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "No portfolio sectors found"));
            }
        } catch (PortfolioSectorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, e.getMessage()));
        }
    }

    @GetMapping("/{portfolioId}/{sector}")
    public ResponseEntity<?> getPortfolioSectorById(@PathVariable String portfolioId, @PathVariable String sector) {
        try {
            PortfolioSector portfolioSector = portfolioSectorService.getPortfolioSectorById(portfolioId, sector);
            if (portfolioSector != null) {
                return ResponseEntity
                        .ok(new ApiResponse(HttpStatus.OK.value(), portfolioSector, "Portfolio sector found"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "Portfolio sector not found"));
            }
        } catch (PortfolioSectorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, e.getMessage()));
        }
    }

    @PostMapping("/{portfolioId}")
    public ResponseEntity<?> createPortfolioSectors(@PathVariable String portfolioId,
            @RequestBody List<Map<String, Object>> stockRequests) {
        try {
            List<PortfolioSector> portfolioSectors = portfolioSectorService.createPortfolioSectors(portfolioId,
                    stockRequests);
            if (!portfolioSectors.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), portfolioSectors,
                        "Portfolio sectors created successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "No portfolio sectors created"));
            }
        } catch (PortfolioSectorException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, e.getMessage()));
        }
    }
}