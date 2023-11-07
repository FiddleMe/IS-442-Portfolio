
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
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/insights")
public class InsightsController {

    private final InsightsService insightsService;

    public InsightsController(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GetMapping("/insights/{portfolioId}")
    public ResponseEntity<?> getAllInsights(@PathVariable String portfolioId) {
        Map<String, BigDecimal> geographicalDistribution = insightsService.getGeographicalDistribution(portfolioId);
        Map<String, BigDecimal> industryDistribution = insightsService.getIndustryDistribution(portfolioId);
        List<Object> profitLoss = insightsService.getProfitLoss(portfolioId);
        Object totalProfitLoss = insightsService.getTotalProfitLoss(portfolioId);
        Map<LocalDate, BigDecimal> historicalReturns = insightsService.getHistoricalReturns(portfolioId, "daily");
        //combine them all into an object
        Map<String, Object> allInsights = new HashMap<>();
        allInsights.put("geographicalDistribution", geographicalDistribution);
        allInsights.put("industryDistribution", industryDistribution);
        allInsights.put("profitLoss", profitLoss);
        allInsights.put("totalProfitLoss", totalProfitLoss);
        allInsights.put("historicalReturns", historicalReturns);        
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), allInsights, "success"));
        
    }



    @GetMapping("/geo-distribution/{portfolioId}")
    public ResponseEntity<?> getGeographicalDistribution(@PathVariable String portfolioId) {

        Map<String, BigDecimal> geographicalDistribution = insightsService.getGeographicalDistribution(portfolioId);

        if (geographicalDistribution != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), geographicalDistribution, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "geographical distribution not found"));
        }
    }

    @GetMapping("/industry-distribution/{portfolioId}")
    public ResponseEntity<?> getIndustryDistribution(@PathVariable String portfolioId) {

        Map<String, BigDecimal> industryDistribution = insightsService.getIndustryDistribution(portfolioId);

        if (industryDistribution != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), industryDistribution, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "industry distribution not found"));
        }
    }

    // Name, Starting value, current value, p&L, %PL, qty, allocation
    @GetMapping("/profit-loss/{portfolioId}")
    public ResponseEntity<?> getProfitLoss(@PathVariable String portfolioId) {
        List<Object> profitLoss = insightsService.getProfitLoss(portfolioId);

        if (profitLoss != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), profitLoss, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "industry distribution not found"));
        }
    }

    @GetMapping("/total-profit-loss/{portfolioId}")
    public ResponseEntity<?> getTotalProfitLoss(@PathVariable String portfolioId) {
        Object totalProfitLoss = insightsService.getTotalProfitLoss(portfolioId);

        if (totalProfitLoss != null) {
            return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), totalProfitLoss, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), null, "industry distribution not found"));
        }
    }

    @GetMapping("/historical-returns/{interval}/{portfolioId}")
    public ResponseEntity<?> getHistoricalReturns(@PathVariable String portfolioId, @PathVariable String interval) {
        Map<LocalDate, BigDecimal> historicalReturns = insightsService.getHistoricalReturns(portfolioId, interval);
        return ResponseEntity.ok(historicalReturns);
    }

    @GetMapping("/redistribution/{portfolioId}")
    public ResponseEntity<Map<String, String>> getRedistributionData(@PathVariable String portfolioId) {
        try {
            Map<String, String> redistributionData = insightsService.getRedistributionData(portfolioId);
            return new ResponseEntity<>(redistributionData, HttpStatus.OK);
        } catch (InsightsException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
