package com.BackendServices.Portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.Portfolio.dto.PortfolioDTO;
import com.BackendServices.Portfolio.dto.UpdatePortfolioDTO;
import com.BackendServices.common.ApiResponse;
import java.util.Collections;
import java.util.List;



@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService porfolioService) {
        this.portfolioService = porfolioService;
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<?> getPortfolioById(@PathVariable String portfolioId) {
        PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
        if (portfolio != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), portfolio, "Portfolio found"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "Portfolio not found"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPortfolios() {
        List<PortfolioDTO> portfolios = portfolioService.getAllPortfolios();
        if (portfolios.isEmpty()) {
            ApiResponse response = new ApiResponse(HttpStatus.NOT_FOUND.value(), Collections.emptyList(), "No portfolios found");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), portfolios, "Portfolios found"));
        }
    }
    @PostMapping
    public ResponseEntity<ApiResponse> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        PortfolioDTO createdPortfolio = portfolioService.createPortfolio(portfolioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(HttpStatus.OK.value(), createdPortfolio, "Portfolio created"));
    }

    @PutMapping("/{portfolioId}")
    public ResponseEntity<?> updatePortfolio(@PathVariable String portfolioId, @RequestBody UpdatePortfolioDTO updatedPortfolioDTO) {
        if (updatedPortfolioDTO.getPortfolioId() != null || updatedPortfolioDTO.getUserId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), null, "Invalid request: 'portfolioId' and 'userId' cannot be updated in the request body."));
        }
    
        PortfolioDTO updatedPortfolio = portfolioService.updatePortfolio(portfolioId, updatedPortfolioDTO);
    
        if (updatedPortfolio != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), updatedPortfolio, "Portfolio updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "Portfolio not found"));
        }
    }
    

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<?> deletePortfolio(@PathVariable String portfolioId) {
        boolean deleted = portfolioService.deletePortfolio(portfolioId);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), null, "Portfolio deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.OK.value(), null, "Portfolio not found"));
        }
    }
}