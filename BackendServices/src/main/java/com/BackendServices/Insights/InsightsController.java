
package com.BackendServices.Insights;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BackendServices.Portfolio.dto.PortfolioDTO;
import com.BackendServices.common.ApiResponse;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/insights")
public class InsightsController {

    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }


    // @GetMapping("/price-distribution")
    // public ResponseEntity<?> getPriceDistribution(@PathVariable String portfolioId) {

    //     Map<String, BigDecimal> priceDistribution = insightsService.getPriceDistribution(portfolioId);
        
    //     if (priceDistribution != null) {
    //         return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), priceDistribution, "suceesss"));
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "Portfolio not found"));
    //     }
    // }
    @GetMapping("/price-distribution/{portfolioId}")
    public ResponseEntity<?> getPriceDistribution(@PathVariable String portfolioId) {

        Map<String, BigDecimal> priceDistribution = insightsService.getPriceDistribution(portfolioId);

        if (priceDistribution != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), priceDistribution, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "price distribution not found"));
        }
    }

    @GetMapping("/geo-distribution/{portfolioId}")
    public ResponseEntity<?> getGeographicalDistribution(@PathVariable String portfolioId) {

        Map<String, BigDecimal> geographicalDistribution = insightsService.getGeographicalDistribution(portfolioId);

        if (geographicalDistribution != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), geographicalDistribution, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "geographical distribution not found"));
        }
    }

    @GetMapping("/industry-distribution/{portfolioId}")
    public ResponseEntity<?> getIndustryDistribution(@PathVariable String portfolioId) {

        Map<String, BigDecimal> industryDistribution = insightsService.getIndustryDistribution(portfolioId);

        if (industryDistribution != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), industryDistribution, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "industry distribution not found"));
        }
    }

    // @GetMapping("/portfolio-stocks")
    // public ResponseEntity<List<PortfolioStocks>> getPortfolioStocks(@PathVariable String portfolioId) {
    //     List<PortfolioStocks> portfolioStocks = insightsService.getPortfolioStocks(portfolioId);
    //     return ResponseEntity.ok(portfolioStocks);
    // }

    // @GetMapping("/geographical-distribution")
    // public ResponseEntity<Map<String, BigDecimal>> getGeographicalDistribution(@PathVariable String portfolioId) {
    //     Map<String, BigDecimal> geographicalDistribution = insightsService.getGeographicalDistribution(portfolioId);
    //     return ResponseEntity.ok(geographicalDistribution);
    // }

    // @GetMapping("/industry-distribution")
    // public ResponseEntity<Map<String, BigDecimal>> getIndustryDistribution(@PathVariable String portfolioId) {
    //     Map<String, BigDecimal> industryDistribution = insightsService.getIndustryDistribution(portfolioId);
    //     return ResponseEntity.ok(industryDistribution);
    // }

    // @GetMapping("/profit-loss")
    // public ResponseEntity<Map<String, BigDecimal>> getProfitLoss(@PathVariable String portfolioId) {
    //     Map<String, BigDecimal> profitLoss = insightsService.getProfitLoss(portfolioId);
    //     return ResponseEntity.ok(profitLoss);
    // }

    // @GetMapping("/profit-loss-percentage")
    // public ResponseEntity<Map<String, BigDecimal>> getProfitLossPercentage(@PathVariable String portfolioId) {
    //     Map<String, BigDecimal> profitLossPercentage = insightsService.getProfitLossPercentage(portfolioId);
    //     return ResponseEntity.ok(profitLossPercentage);
    // }

    // @GetMapping("/historical-returns/{interval}")
    // public ResponseEntity<Map<String, BigDecimal>> getHistoricalReturns(@PathVariable String portfolioId, @PathVariable String interval) {
    //     Map<String, BigDecimal> historicalReturns = insightsService.getHistoricalReturns(portfolioId, interval);
    //     return ResponseEntity.ok(historicalReturns);
    // }

    // Add additional endpoints for other insights-related methods as needed.
}
