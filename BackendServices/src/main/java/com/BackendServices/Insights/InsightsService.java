package com.BackendServices.Insights;

import com.BackendServices.PortfolioStock.PortfolioStocks;
import com.BackendServices.PortfolioStock.PortfolioStocksService;
import com.BackendServices.Portfolio.Portfolio;
import com.BackendServices.Stock.Stock;
import com.BackendServices.Portfolio.dto.PortfolioDTO;
import com.BackendServices.PortfolioSector.PortfolioSector;
import com.BackendServices.PortfolioSector.PortfolioSectorService;
import com.BackendServices.Portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Value;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.math.MathContext;
import org.springframework.stereotype.Service;
import com.BackendServices.Portfolio.dto.*;
import java.math.RoundingMode;
@Service
public class InsightsService {
    private final PortfolioStocksService portfolioStocksService;
    private final PortfolioService portfolioService;
    private final PortfolioSectorService portfolioSectorService;
    public List<PortfolioStocks> portfolioStocksList;

    public InsightsService(PortfolioStocksService portfolioStocksService, PortfolioService portfolioService,
            PortfolioSectorService portfolioSectorService) {
        this.portfolioService = portfolioService;
        this.portfolioStocksService = portfolioStocksService;
        this.portfolioSectorService = portfolioSectorService;
    }


    public Map<String, BigDecimal> getGeographicalDistribution(String portfolioId) {
        Map<String, BigDecimal> geographicalDistribution = new HashMap<>();
        BigDecimal totalCapital = BigDecimal.ZERO;
        portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);

        for (PortfolioStocks portfolioStock : portfolioStocksList) {
            String stockId = portfolioStock.getStockId();
            BigDecimal lastestPrice = portfolioStocksService.getLatestPrice(stockId);
            Integer quantity = portfolioStock.getQuantity();
            BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity));
            String region = portfolioStocksService.getGeographicalRegion(stockId);
            totalCapital = totalCapital.add(totalPrice);

            if (geographicalDistribution.containsKey(region)) {
                BigDecimal currentAllocation = geographicalDistribution.get(region);
                currentAllocation = currentAllocation.add(totalPrice);
                geographicalDistribution.put(region, currentAllocation);
            } else {                
                geographicalDistribution.put(region, totalPrice);
            }
        }

        for (Map.Entry<String, BigDecimal> entry : geographicalDistribution.entrySet()) {
            String regionName = entry.getKey();
            BigDecimal stockPrice = entry.getValue();
            BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); 
            geographicalDistribution.put(regionName, allocationPercentage);
        }

        return geographicalDistribution;
    }

    public Map<String, BigDecimal> getIndustryDistribution(String portfolioId) {
        Map<String, BigDecimal> industryDistribution = new HashMap<>();
        BigDecimal totalCapital = BigDecimal.ZERO;
        portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);

        for (PortfolioStocks portfolioStock : portfolioStocksList) {
            String stockId = portfolioStock.getStockId();
            BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
            Integer quantity = portfolioStock.getQuantity();
            BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
            String industry = portfolioStocksService.getIndustrySector(stockId);
            totalCapital = totalCapital.add(totalPrice);

            if (industryDistribution.containsKey(industry)) {
                BigDecimal currentAllocation = industryDistribution.get(industry);
                currentAllocation = currentAllocation.add(totalPrice);
                industryDistribution.put(industry, currentAllocation);
            } else {
                industryDistribution.put(industry, totalPrice);
            }
        }

        for (Map.Entry<String, BigDecimal> entry : industryDistribution.entrySet()) {
            String industryName = entry.getKey();
            BigDecimal stockPrice = entry.getValue();
            BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); 
            industryDistribution.put(industryName, allocationPercentage);
        }

        return industryDistribution;
    }

    public List<Object> getProfitLoss(String portfolioId) {
        List<Object> out = new ArrayList<>();

        portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);

        Map<String, BigDecimal> priceDistribution = new HashMap<>();
        PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
        BigDecimal totalCapital = portfolio.getCapitalAmount();

        for (PortfolioStocks portfolioStock : portfolioStocksList) {

            String stockId = portfolioStock.getStockId();
            BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
            Integer quantity = portfolioStock.getQuantity();
            BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
            String name = portfolioStocksService.getName(stockId);
            totalCapital = totalCapital.add(totalPrice);

            if (priceDistribution.containsKey(name)) {
                BigDecimal currentAllocation = priceDistribution.get(name);
                currentAllocation = currentAllocation.add(totalPrice);
                priceDistribution.put(name, currentAllocation);
            } else {
                priceDistribution.put(name, totalPrice);
            }
        }

        for (Map.Entry<String, BigDecimal> entry : priceDistribution.entrySet()) {
            String stockName = entry.getKey();
            BigDecimal stockPrice = entry.getValue();
            BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); 
            priceDistribution.put(stockName, allocationPercentage);
        }

        for (PortfolioStocks portfolioStock : portfolioStocksList) {
            String stockId = portfolioStock.getStockId();
            int quantity = portfolioStock.getQuantity();
            LocalDate purchaseDate = portfolioStock.getdate();
            BigDecimal currentPrice = portfolioStocksService.getLatestPrice(stockId);
            BigDecimal purchasePrice = portfolioStocksService.getPurchasePrice(stockId, purchaseDate);
            BigDecimal priceDifference = currentPrice.subtract(purchasePrice);
            String name = portfolioStocksService.getName(stockId);
            BigDecimal profitLossPercentage = BigDecimal.ZERO;
            if (!purchasePrice.equals(BigDecimal.ZERO)) {
                profitLossPercentage = priceDifference.divide(purchasePrice, new MathContext(4));
            }

            Map<String, Object> stockInfo = new HashMap<>();
            stockInfo.put("name", name);
            stockInfo.put("qty", quantity);
            stockInfo.put("currentPrice", currentPrice);
            stockInfo.put("purchasePrice", purchasePrice);
            stockInfo.put("priceDifference", priceDifference);
            stockInfo.put("profitLossPercentage", profitLossPercentage);
            stockInfo.put("allocation", priceDistribution.get(name));

            out.add(stockInfo);
        }

        return out;
    }

    public Map<String, Object> getTotalProfitLoss(String portfolioId) {
        BigDecimal totalPortfolioValue = BigDecimal.ZERO;
        BigDecimal totalOriginalPortfolioValue = BigDecimal.ZERO;
        BigDecimal totalProfitLoss = BigDecimal.ZERO;
        BigDecimal totalProfitLossPercentage = BigDecimal.ZERO;
        portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);
        for (PortfolioStocks portfolioStock : portfolioStocksList) {
            int quantity = portfolioStock.getQuantity();
            String stockId = portfolioStock.getStockId();
            LocalDate purchaseDate = portfolioStock.getdate();
            BigDecimal currentPrice = portfolioStocksService.getLatestPrice(stockId);
            BigDecimal purchasePrice = portfolioStocksService.getPurchasePrice(stockId, purchaseDate);
            BigDecimal priceDifference = currentPrice.subtract(purchasePrice);
            BigDecimal profitLoss = priceDifference.multiply(BigDecimal.valueOf(quantity));
            BigDecimal originalPortfolioValue = purchasePrice.multiply(BigDecimal.valueOf(quantity));
            totalOriginalPortfolioValue = totalOriginalPortfolioValue.add(originalPortfolioValue);
            totalProfitLoss = totalProfitLoss.add(profitLoss);
            BigDecimal portfolioValue = currentPrice.multiply(BigDecimal.valueOf(quantity));
            totalPortfolioValue = totalPortfolioValue.add(portfolioValue);
        }

        BigDecimal profitLossPercentage = BigDecimal.ZERO;
        if (!totalProfitLoss.equals(BigDecimal.ZERO)) {
            profitLossPercentage = totalProfitLoss.divide(totalOriginalPortfolioValue, new MathContext(4));
        }
        totalProfitLossPercentage = totalProfitLossPercentage.add(profitLossPercentage);
        Map<String, Object> totalPortfolioInfo = new HashMap<>();
        totalPortfolioInfo.put("totalPortfolioValue", totalPortfolioValue);
        totalPortfolioInfo.put("totalOriginalPortfolioValue", totalOriginalPortfolioValue);
        totalPortfolioInfo.put("totalProfitLoss", totalProfitLoss);
        totalPortfolioInfo.put("totalProfitLossPercentage", totalProfitLossPercentage);

        return totalPortfolioInfo;
    }

    public Map<LocalDate, BigDecimal> getHistoricalReturns(String portfolioId, String interval) {
        Map<LocalDate, BigDecimal> historicalReturns = new LinkedHashMap<>();
        portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);
        PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);

        int numberOfIntervals = 40;
        int intervalCount = 0;

        if (interval.equals("daily")) {
            intervalCount = 1;
        }
        if (interval.equals("weekly")) {
            intervalCount = 7;
        }
        if (interval.equals("monthly")) {
            intervalCount = 28;
        }
        int totalDuration = intervalCount * numberOfIntervals;
        LocalDate latestDate = portfolioStocksService.getLatestDate();

        for (int i = totalDuration; i >= 0; i -= intervalCount) {
            LocalDate date = latestDate.minusDays(i);
            BigDecimal totalValue = BigDecimal.ZERO;
            for (PortfolioStocks portfolioStock : portfolioStocksList) {
                try {
                    String stockId = portfolioStock.getStockId();                    
                    BigDecimal lastestPrice = portfolioStocksService.getPurchasePrice(stockId, date);
                    Integer quantity = portfolioStock.getQuantity();                    
                    BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity))
                            .add(portfolio.getCapitalAmount());
                    totalValue = totalValue.add(totalPrice);
                } catch (Exception e) {
                    // e.printStackTrace();
                    continue;
                }

            }
            System.out.println("date: " + date + "  totalvalue: " + totalValue);
            if (!totalValue.equals(BigDecimal.ZERO)) {
                historicalReturns.put(date, totalValue.setScale(2, RoundingMode.HALF_UP));
            }
        }
        return historicalReturns;
    }

    public Map<String, String> getRedistributionData(String portfolioId) throws InsightsException {
        Map<String, String> redistributionData = new HashMap<>();
        List<PortfolioSector> portfolioSectors = portfolioSectorService.getPortfolioSectorsByPortfolioId(portfolioId);
        Map<String, BigDecimal> currentDistribution = this.getIndustryDistribution(portfolioId);
       

        for (Map.Entry<String, BigDecimal> entry : currentDistribution.entrySet()) {
            String currentSector = entry.getKey();
            BigDecimal currentValue = entry.getValue().multiply(new BigDecimal("100"));
            
            PortfolioSector initial = portfolioSectors.stream()
            .filter(ps -> ps.getSector().equals(currentSector))
            .findFirst()
            .orElse(null);
            
            System.out.println(portfolioSectors.get(0).getSector());
            System.out.println(currentSector);

            BigDecimal initialValue = initial.getPercentage();
            System.out.println(currentValue.toString());
            System.out.println(initialValue.toString());

            if (currentValue.compareTo(initialValue) > 0) {
                redistributionData.put(currentSector, "sell");
            } else if (currentValue.compareTo(initialValue) < 0) {
                redistributionData.put(currentSector, "buy");
            } else {
                redistributionData.put(currentSector, "same");
            }
        }

        return redistributionData;
    }

}
