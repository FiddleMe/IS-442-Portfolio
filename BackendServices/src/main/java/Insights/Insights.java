import com.BackendServices.PortfolioStock.PortfolioStocks;
import com.BackendServices.PortfolioStock.PortfolioStocksService;
import com.BackendServices.Portfolio.Portfolio;
import com.BackendServices.Portfolio.PortfolioService;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import java.math.MathContext;

public class Insights extends Portfolio {
    private PortfolioStocksService portfolioStocksService;
    private PortfolioService portfolioService;
    public List<PortfolioStocks> portfolioStocks;
    public Insights(){

    }
    public Insights(PortfolioStocksService portfolioStocksService, PortfolioService portfolioService) {
        super();
        this.portfolioStocksService = portfolioStocksService;
        this.portfolioService = portfolioService;
    }
//get portfolioId from somewhere
public void initialize(String portfolioId, String name, String description, BigDecimal capitalAmount, String userId) {
    setName(name);
    setDescription(description);
    List<PortfolioStocks> portfolioStocks = portfolioStocksService.getPortfolioStocksById(portfolioId);
    setPortfolioStocks(portfolioStocks);
    setCapitalAmount(capitalAmount);
    setUserId(userId);
}

public List<PortfolioStocks> getPortfolioStocks(String portfolioId) {
    return portfolioStocksService.getPortfolioStocksById(portfolioId);
}

public Map<String, BigDecimal> getPriceDistribution() {
    // Price distribution of the ArrayList of stocks
    Map<String, BigDecimal> priceDistribution = new HashMap<>();
    BigDecimal totalCapital = getCapitalAmount();
    // Assuming capitalAmount is the total amount of portfolioValue;
    for (PortfolioStocks portfolioStock : portfolioStocks) {
        String stockId = portfolioStock.getStockId();
        BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
        Integer quantity = portfolioStock.getQuantity();
        BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
        String name = portfolioStocksService.getName(stockId);

        if (priceDistribution.containsKey(name)) {
            // If the stock name is already in the map, add the totalPrice to the existing value
            BigDecimal currentAllocation = priceDistribution.get(name);
            currentAllocation = currentAllocation.add(totalPrice.divide(totalCapital, new MathContext(4))); // 4 decimal places, you can adjust as needed
            priceDistribution.put(name, currentAllocation);
        } else {
            // If the stock name is not in the map, create a new entry
            BigDecimal allocationPercentage = totalPrice.divide(totalCapital, new MathContext(4)); // 4 decimal places, you can adjust as needed
            priceDistribution.put(name, allocationPercentage);
        }
    }
    return priceDistribution;
}


public Map<String, BigDecimal> getGeographicalDistribution() {
    // Geographical distribution of the ArrayList of stocks
    Map<String, BigDecimal> geographicalDistribution = new HashMap<>();
    BigDecimal totalCapital = getCapitalAmount();
    // Assuming capitalAmount is the total amount of portfolioValue;
    for (PortfolioStocks portfolioStock : portfolioStocks) {
        String stockId = portfolioStock.getStockId();
        BigDecimal lastestPrice = portfolioStocksService.getLatestPrice(stockId);
        Integer quantity = portfolioStock.getQuantity();
        BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity));
        String region = portfolioStocksService.getGeographicalRegion(stockId);

        if (geographicalDistribution.containsKey(region)) {
            // If the region is already in the map, add the totalPrice to the existing value
            BigDecimal currentAllocation = geographicalDistribution.get(region);
            currentAllocation = currentAllocation.add(totalPrice.divide(totalCapital, new MathContext(4))); // 4 decimal places, you can adjust as needed
            geographicalDistribution.put(region, currentAllocation);
        } else {
            // If the region is not in the map, create a new entry
            BigDecimal allocationPercentage = totalPrice.divide(totalCapital, new MathContext(4)); // 4 decimal places, you can adjust as needed
            geographicalDistribution.put(region, allocationPercentage);
        }
    }
    return geographicalDistribution;
}

public Map<String, BigDecimal> getIndustryDistribution() {
    // Industry distribution of the ArrayList of stocks
    Map<String, BigDecimal> industryDistribution = new HashMap<>();
    BigDecimal totalCapital = getCapitalAmount(); // Assuming capitalAmount is the total amount of portfolioValue;

    for (PortfolioStocks portfolioStock : portfolioStocks) {
        String stockId = portfolioStock.getStockId();
        BigDecimal latestPrice = portfolioStocksService.getLatestPrice(stockId);
        Integer quantity = portfolioStock.getQuantity();
        BigDecimal totalPrice = latestPrice.multiply(BigDecimal.valueOf(quantity));
        String industry = portfolioStocksService.getIndustrySector(stockId);

        if (industryDistribution.containsKey(industry)) {
            // If the industry is already in the map, add the totalPrice to the existing value
            BigDecimal currentAllocation = industryDistribution.get(industry);
            currentAllocation = currentAllocation.add(totalPrice.divide(totalCapital, new MathContext(4))); // 4 decimal places, you can adjust as needed
            industryDistribution.put(industry, currentAllocation);
        } else {
            // If the industry is not in the map, create a new entry
            BigDecimal allocationPercentage = totalPrice.divide(totalCapital, new MathContext(4)); // 4 decimal places, you can adjust as needed
            industryDistribution.put(industry, allocationPercentage);
        }
    }
    return industryDistribution;
}


public Map<String, BigDecimal> getProfitLoss() {
    // Profit loss of the ArrayList of stocks
    Map<String, BigDecimal> profitLossMap = new HashMap<>();

    for (PortfolioStocks portfolioStock : portfolioStocks) {
        String stockId = portfolioStock.getStockId();
        LocalDate date = portfolioStock.getdate();
        BigDecimal profitLoss = portfolioStocksService.getStockPriceChange(stockId, date);
        String name = portfolioStocksService.getName(stockId);
        profitLossMap.put(name, profitLoss);
    }
    return profitLossMap;
}

public Map<String, BigDecimal> getProfitLossPercentage() {
    // Profit loss percentage of the ArrayList of stocks
    Map<String, BigDecimal> profitLossPercentageMap = new HashMap<>();
    
    for (PortfolioStocks portfolioStock : portfolioStocks) {
        String stockId = portfolioStock.getStockId();
        LocalDate date = portfolioStock.getdate();
        BigDecimal purchasePrice = portfolioStocksService.getPurchasePrice(stockId, date);
        BigDecimal profitLoss = portfolioStocksService.getStockPriceChange(stockId, date);
        String name = portfolioStocksService.getName(stockId);
        BigDecimal profitLossPercentage = profitLoss.divide(purchasePrice, new MathContext(4));
        profitLossPercentageMap.put(name, profitLossPercentage);

    }
    return profitLossPercentageMap;
}

public Map<LocalDate, BigDecimal> getHistoricalReturns(String interval) {
    Map<LocalDate, BigDecimal> historicalReturns = new HashMap<>();

    int numberOfIntervals = 3;
    int intervalCount = 0;

    if (interval.equals("daily")) {
        intervalCount = 1;
    }
    if (interval.equals("weekly")) {
        intervalCount = 7;
    }
    if (interval.equals("monthly")) {
        intervalCount = 30;
    }
    int totalDuration = intervalCount * numberOfIntervals;

    LocalDate currentDate = LocalDate.now();

    for (int i = 0; i < totalDuration; i += intervalCount) {
        LocalDate date = currentDate.plusDays(i);
        // Get the total value at currentDate
        BigDecimal totalValue = BigDecimal.ZERO;
        for (PortfolioStocks portfolioStock : portfolioStocks) {
            String stockId = portfolioStock.getStockId();
            BigDecimal lastestPrice = portfolioStocksService.getPurchasePrice(stockId, date);
            Integer quantity = portfolioStock.getQuantity();
            BigDecimal totalPrice = lastestPrice.multiply(BigDecimal.valueOf(quantity));
            totalValue = totalValue.add(totalPrice);
        }

        historicalReturns.put(date, totalValue);
    }
    return historicalReturns;
}




public void setName(String name) {
    super.setName(name);
}

public void setDescription(String description) {
    super.setDescription(description);
}

public void setPortfolioStocks(List<PortfolioStocks> portfolioStocks) {
    this.portfolioStocks = portfolioStocks;
}

public void setCapitalAmount(BigDecimal capitalAmount) {
    super.setCapitalAmount(capitalAmount);
}
  
}

