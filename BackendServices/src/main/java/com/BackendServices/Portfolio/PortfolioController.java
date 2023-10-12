package com.BackendServices.Portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.Portfolio.dto.PortfolioDTO;
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
    //Test: POST http://localhost:8082/api/users, body:  {
    //     "userId": "new_user_id",
    //     "email": "newuser@example.com",
    //     "password": "asfasfasf",
    //     "firstName": "John",
    //     "lastName": "Doe"
    // }

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

    // @PutMapping("/{portfolioId}")
    // public ResponseEntity<PortfolioDTO> updatePortfolio(@PathVariable UUID portfolioId, @RequestBody PortfolioDTO updatedPortfolio) {
    //     PortfolioDTO portfolio = portfolioService.updatePortfolio(portfolioId, updatedPortfolio);
    //     if (portfolio != null) {
    //         return ResponseEntity.ok(portfolio);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

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