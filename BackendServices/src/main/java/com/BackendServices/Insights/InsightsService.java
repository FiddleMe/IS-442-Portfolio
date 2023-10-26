package com.BackendServices.Insights;
import com.BackendServices.PortfolioStock.PortfolioStocks;
import com.BackendServices.PortfolioStock.PortfolioStocksService;
import com.BackendServices.Portfolio.Portfolio;
import com.BackendServices.Stock.Stock;
import com.BackendServices.Portfolio.dto.PortfolioDTO;
import com.BackendServices.Portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import java.math.MathContext;
import org.springframework.stereotype.Service;
import com.BackendServices.Portfolio.dto.*;

@Service
public class InsightsService {
    private final PortfolioStocksService portfolioStocksService;
    // private final PortfolioStocks portfolioStocks;
    private final PortfolioService portfolioService;
    // private final Portfolio portfolio;
    public List<PortfolioStocks> portfolioStocksList;

    public InsightsService(PortfolioStocksService portfolioStocksService, PortfolioService portfolioService) {
        // this.portfolio = portfolio;
        this.portfolioService = portfolioService;
        // this.portfolioStocks = portfolioStocks;
        this.portfolioStocksService = portfolioStocksService;
    }

    public Map<String, BigDecimal> getPriceDistribution(String portfolioId) {
      // Price distribution of the ArrayList of stocks
      System.out.println("portfolioId: " + portfolioId);
      // //initialise Portfolio using portfolioId

      PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
      System.out.println(portfolio.getName());
      System.out.println(portfolio.getCapitalAmount());

      System.out.println("portfolio: " + portfolio);

      Map<String, BigDecimal> priceDistribution = new HashMap<>();
      
      BigDecimal totalCapital = portfolio.getCapitalAmount();
      
      // Assuming capitalAmount is the total amount of portfolioValue,
      portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);
      for (PortfolioStocks portfolioStock : portfolioStocksList) {
          System.out.println("PORTFOLIO STOCK" + portfolioStock.getPortfolioStockId());
          String stockId = portfolioStock.getStockId();
          System.out.println("STOCKID" + stockId);

          BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
          System.out.println("lastestprice" + latestPrice);
          Integer quantity = portfolioStock.getQuantity();
          System.out.println("lquantity" + quantity);
          BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
          System.out.println("toalprice" + totalPrice);
          String name = portfolioStocksService.getName(stockId);
          totalCapital = totalCapital.add(totalPrice);
          System.out.println(totalCapital);
  
          if (priceDistribution.containsKey(name)) {
              // If the stock name is already in the map, add the totalPrice to the existing value
              BigDecimal currentAllocation = priceDistribution.get(name);
              currentAllocation = currentAllocation.add(totalPrice); 
              priceDistribution.put(name, currentAllocation);
          } else {
              // If the stock name is not in the map, create a new entry
              priceDistribution.put(name, totalPrice);
          }
      }
      
      for (Map.Entry<String, BigDecimal> entry : priceDistribution.entrySet()) {
        String stockName = entry.getKey();
        BigDecimal stockPrice = entry.getValue();
        System.out.println(stockPrice);
        System.out.println(totalCapital);
        BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
        priceDistribution.put(stockName, allocationPercentage);
    }

      return priceDistribution;
  }
  
  // public List<PortfolioStocks> getPortfolioStocks(String portfolioId) {
  //   return portfolioStocksService.getPortfolioStocksById(portfolioId);
  // }
  public Map<String, BigDecimal> getGeographicalDistribution(String portfolioId) {
      // Geographical distribution of the ArrayList of stocks
      Map<String, BigDecimal> geographicalDistribution = new HashMap<>();
      // PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
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
              // If the region is already in the map, add the totalPrice to the existing value
              BigDecimal currentAllocation = geographicalDistribution.get(region);
              currentAllocation = currentAllocation.add(totalPrice);
              geographicalDistribution.put(region, currentAllocation);
          } else {
              // If the region is not in the map, create a new entry
              geographicalDistribution.put(region, totalPrice);
          }
      }

      for (Map.Entry<String, BigDecimal> entry : geographicalDistribution.entrySet()) {
        String regionName = entry.getKey();
        BigDecimal stockPrice = entry.getValue();
        System.out.println(stockPrice);
        System.out.println(regionName);
        System.out.println(totalCapital);
        BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
        geographicalDistribution.put(regionName, allocationPercentage);
      }

      return geographicalDistribution;
  }
  
  public Map<String, BigDecimal> getIndustryDistribution(String portfolioId) {
      // Industry distribution of the ArrayList of stocks
      Map<String, BigDecimal> industryDistribution = new HashMap<>();
      // PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
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
              // If the industry is already in the map, add the totalPrice to the existing value
              BigDecimal currentAllocation = industryDistribution.get(industry);
              currentAllocation = currentAllocation.add(totalPrice);
              industryDistribution.put(industry, currentAllocation);
          } else {
              // If the industry is not in the map, create a new entry
              industryDistribution.put(industry, totalPrice);
          }
      }
      for (Map.Entry<String, BigDecimal> entry : industryDistribution.entrySet()) {
        String industryName = entry.getKey();
        BigDecimal stockPrice = entry.getValue();
        System.out.println(stockPrice);
        System.out.println(industryName);
        System.out.println(totalCapital);
        BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
        industryDistribution.put(industryName, allocationPercentage);
      }

      
      return industryDistribution;
  }
  
  
//   public Map<String, BigDecimal> getProfitLoss() {
//       // Profit loss of the ArrayList of stocks
//       Map<String, BigDecimal> profitLossMap = new HashMap<>();
  
//       for (PortfolioStocks portfolioStock : portfolioStocks) {
//           String stockId = portfolioStock.getStockId();
//           LocalDate date = portfolioStock.getdate();
          
//           Object priceDifferenceObj = portfolioStocksService.getStockPriceChange(stockId, date).get("priceDifference");
//           double priceDifference = ((Number) priceDifferenceObj).doubleValue();
//           String name = portfolioStocksService.getName(stockId);
//           profitLossMap.put(name, profitLoss);
//       }
//       return profitLossMap;
//   }
  
//   public Map<String, BigDecimal> getProfitLossPercentage() {
//       // Profit loss percentage of the ArrayList of stocks
//       Map<String, BigDecimal> profitLossPercentageMap = new HashMap<>();
      
//       for (PortfolioStocks portfolioStock : portfolioStocks) {
//           String stockId = portfolioStock.getStockId();
//           LocalDate date = portfolioStock.getdate();
//           BigDecimal purchasePrice = portfolioStocksService.getPurchasePrice(stockId, date);
//           BigDecimal profitLoss = portfolioStocksService.getStockPriceChange(stockId, date);
//           String name = portfolioStocksService.getName(stockId);
//           BigDecimal profitLossPercentage = profitLoss.divide(purchasePrice, new MathContext(4));
//           profitLossPercentageMap.put(name, profitLossPercentage);
  
//       }
//       return profitLossPercentageMap;
//   }
  
//   public Map<LocalDate, BigDecimal> getHistoricalReturns(String interval) {
//       Map<LocalDate, BigDecimal> historicalReturns = new HashMap<>();
  
//       int numberOfIntervals = 3;
//       int intervalCount = 0;
  
//       if (interval.equals("daily")) {
//           intervalCount = 1;
//       }
//       if (interval.equals("weekly")) {
//           intervalCount = 7;
//       }
//       if (interval.equals("monthly")) {
//           intervalCount = 30;
//       }
//       int totalDuration = intervalCount * numberOfIntervals;
  
//       LocalDate currentDate = LocalDate.now();
  
//       for (int i = 0; i < totalDuration; i += intervalCount) {
//           LocalDate date = currentDate.plusDays(i);
//           // Get the total value at currentDate
//           BigDecimal totalValue = BigDecimal.ZERO;
//           for (PortfolioStocks portfolioStock : portfolioStocks) {
//               String stockId = portfolioStock.getStockId();
//               BigDecimal lastestPrice = portfolioStocksService.getPurchasePrice(stockId, date);
//               Integer quantity = portfolioStock.getQuantity();
//               BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity));
//               totalValue = totalValue.add(totalPrice);
//           }
  
//           historicalReturns.put(date, totalValue);
//       }
//       return historicalReturns;
//   }
  
}
