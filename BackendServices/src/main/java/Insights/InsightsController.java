package com.BackendServices.insights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.Map;
import java.math.BigDecimal;



@RestController
@RequestMapping("/insights")
public class InsightsController {

    private final Insights insights;

    @Autowired
    public InsightsController(Insights insights) {
        this.insights = insights;
    }

    @GetMapping("/price-distribution/{portfolioId}")
    public ResponseEntity<Map<String, BigDecimal>> getPriceDistribution(@PathVariable String portfolioId) {
        return ResponseEntity.ok(insights.getPriceDistribution());
    }

    @GetMapping("/geographical-distribution/{portfolioId}")
    public ResponseEntity<Map<String, BigDecimal>> getGeographicalDistribution(@PathVariable String portfolioId) {
        return ResponseEntity.ok(insights.getGeographicalDistribution());
    }

    @GetMapping("/industry-distribution/{portfolioId}")
    public ResponseEntity<Map<String, BigDecimal>> getIndustryDistribution(@PathVariable String portfolioId) {
        return ResponseEntity.ok(insights.getIndustryDistribution());
    }

    @GetMapping("/profit-loss/{portfolioId}")
    public ResponseEntity<Map<String, BigDecimal>> getProfitLoss(@PathVariable String portfolioId) {
        return ResponseEntity.ok(insights.getProfitLoss());
    }

    @GetMapping("/profit-loss-percentage/{portfolioId}")
    public ResponseEntity<Map<String, BigDecimal>> getProfitLossPercentage(@PathVariable String portfolioId) {
        return ResponseEntity.ok(insights.getProfitLossPercentage());
    }

    @GetMapping("/historical-returns/{portfolioId}")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getHistoricalReturns(
            @PathVariable String portfolioId,
            @RequestParam String interval) {
        return ResponseEntity.ok(insights.getHistoricalReturns(interval));
    }
}
