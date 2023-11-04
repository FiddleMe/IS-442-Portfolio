package com.BackendServices.Insights;
import com.BackendServices.PortfolioStock.PortfolioStocks;
import com.BackendServices.PortfolioStock.PortfolioStocksService;
import com.BackendServices.Portfolio.Portfolio;
import com.BackendServices.Stock.Stock;
import com.BackendServices.Portfolio.dto.PortfolioDTO;
import com.BackendServices.Portfolio.PortfolioService;
import org.springframework.beans.factory.annotation.Value;
import java.util.LinkedHashMap;
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

//     public Map<String, BigDecimal> getPriceDistribution(String portfolioId) {
//       // Price distribution of the ArrayList of stocks
//     //   System.out.println("portfolioId: " + portfolioId);
//       // //initialise Portfolio using portfolioId

//       PortfolioDTO portfolio = portfolioService.getPortfolioById(portfolioId);
//     //   System.out.println(portfolio.getName());
//     //   System.out.println(portfolio.getCapitalAmount());

//     //   System.out.println("portfolio: " + portfolio);

//       Map<String, BigDecimal> priceDistribution = new HashMap<>();
      
//       BigDecimal totalCapital = portfolio.getCapitalAmount();

//       portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);
//       for (PortfolioStocks portfolioStock : portfolioStocksList) {
//         //   System.out.println("PORTFOLIO STOCK" + portfolioStock.getPortfolioStockId());
//           String stockId = portfolioStock.getStockId();
//         //   System.out.println("STOCKID" + stockId);

//           BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
//         //   System.out.println("lastestprice" + latestPrice);
//           Integer quantity = portfolioStock.getQuantity();
//         //   System.out.println("lquantity" + quantity);
//           BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
//         //   System.out.println("toalprice" + totalPrice);
//           String name = portfolioStocksService.getName(stockId);
//           totalCapital = totalCapital.add(totalPrice);
//         //   System.out.println(totalCapital);
  
//           if (priceDistribution.containsKey(name)) {
//               // If the stock name is already in the map, add the totalPrice to the existing value
//               BigDecimal currentAllocation = priceDistribution.get(name);
//               currentAllocation = currentAllocation.add(totalPrice); 
//               priceDistribution.put(name, currentAllocation);
//           } else {
//               // If the stock name is not in the map, create a new entry
//               priceDistribution.put(name, totalPrice);
//           }
//       }
      
//       for (Map.Entry<String, BigDecimal> entry : priceDistribution.entrySet()) {
//         String stockName = entry.getKey();
//         BigDecimal stockPrice = entry.getValue();
//         // System.out.println(stockPrice);
//         // System.out.println(totalCapital);
//         BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
//         priceDistribution.put(stockName, allocationPercentage);
//     }

//       return priceDistribution;
//   }

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
        // System.out.println(stockPrice);
        // System.out.println(regionName);
        // System.out.println(totalCapital);
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
        // System.out.println(stockPrice);
        // System.out.println(industryName);
        // System.out.println(totalCapital);
        BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
        industryDistribution.put(industryName, allocationPercentage);
      }

      
      return industryDistribution;
  }
  
  
  public List<Object> getProfitLoss(String portfolioId) {
      // Profit loss percentage of the ArrayList of stocks
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
            BigDecimal allocationPercentage = stockPrice.divide(totalCapital, new MathContext(4)); // Adjust the scale and rounding mode as needed
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
    // Total portfolio value for all stocks
    BigDecimal totalPortfolioValue = BigDecimal.ZERO;

    // Total original portfolio value for all stocks
    BigDecimal totalOriginalPortfolioValue = BigDecimal.ZERO;

    // Total profit and loss for all stocks
    BigDecimal totalProfitLoss = BigDecimal.ZERO;

    // Total profit/loss percentage
    BigDecimal totalProfitLossPercentage = BigDecimal.ZERO;

    portfolioStocksList = portfolioStocksService.getPortfolioStocksById(portfolioId);

    for (PortfolioStocks portfolioStock : portfolioStocksList) {
        int quantity = portfolioStock.getQuantity();

        String stockId = portfolioStock.getStockId();
        LocalDate purchaseDate = portfolioStock.getdate();

        BigDecimal currentPrice = portfolioStocksService.getLatestPrice(stockId);
        BigDecimal purchasePrice = portfolioStocksService.getPurchasePrice(stockId, purchaseDate);

        BigDecimal priceDifference = currentPrice.subtract(purchasePrice);

        // Calculate profitLoss for the current stock
        BigDecimal profitLoss = priceDifference.multiply(BigDecimal.valueOf(quantity));

        // Calculate profitLossPercentage for the current stock


        // Calculate the original portfolio value for the current stock
        BigDecimal originalPortfolioValue = purchasePrice.multiply(BigDecimal.valueOf(quantity));

        // Add original portfolio value for the current stock to the total
        totalOriginalPortfolioValue = totalOriginalPortfolioValue.add(originalPortfolioValue);

        // Add profitLoss for the current stock to the total
        totalProfitLoss = totalProfitLoss.add(profitLoss);

        // Add profitLossPercentage for the current stock to the total
        

        // Calculate the current portfolio value for the current stock
        BigDecimal portfolioValue = currentPrice.multiply(BigDecimal.valueOf(quantity));

        // Add current portfolio value for the current stock to the total
        totalPortfolioValue = totalPortfolioValue.add(portfolioValue);
    }

    // Create a map to store the total portfolio information
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
      
      int numberOfIntervals = 50;
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
            try{
              String stockId = portfolioStock.getStockId();
            //   System.out.println("stock: " + stockId);
              BigDecimal lastestPrice = portfolioStocksService.getPurchasePrice(stockId, date);
              Integer quantity = portfolioStock.getQuantity();
            //   System.out.println("quantity: " + quantity);
              BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity)).add(portfolio.getCapitalAmount());
              totalValue = totalValue.add(totalPrice);
            }
            catch (Exception e) {
            //   e.printStackTrace(); 
              continue;
          }

          }
          System.out.println("date: " + date + "  totalvalue: " + totalValue);
          if (!totalValue.equals(BigDecimal.ZERO)){
          historicalReturns.put(date, totalValue);
          }
      }
      return historicalReturns;
  }
  
}
