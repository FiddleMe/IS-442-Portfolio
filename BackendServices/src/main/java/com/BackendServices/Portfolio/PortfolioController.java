package com.BackendServices.Portfolio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BackendServices.Portfolio.dto.PortfolioDTO;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService porfolioService) {
        this.portfolioService = porfolioService;
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDTO> getPortfolioById(@PathVariable UUID portfolioId) {
        PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
        if (portfolio != null) {
            return ResponseEntity.ok(portfolio);
        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<List<PortfolioDTO>> getAllPortfolios() {
        List<PortfolioDTO> portfolios = portfolioService.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }

    @PostMapping
    public ResponseEntity<PortfolioDTO> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        PortfolioDTO createdPortfolio = portfolioService.createPortfolio(portfolioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
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
    public ResponseEntity<Boolean> deletePortfolio(@PathVariable UUID portfolioId) {
        boolean deleted = portfolioService.deletePortfolio(portfolioId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}