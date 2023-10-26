package com.BackendServices.Stock.dto;

import com.BackendServices.Stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StockDTO {

    private String stockId;
    private LocalDate dateTime;
    private String name;
    private BigDecimal price;
    private String geographicalRegion;
    private String industrySector;

    public static List<Stock> mapJsonToStockDTO(Map<String, Object> timeSeriesData,
            Map<String, String> companyOverviewData, LocalDate latestDate) {
        List<Stock> stockDTOList = new ArrayList<>();

        Map<String, Map<String, String>> timeSeriesDaily = (Map<String, Map<String, String>>) timeSeriesData
                .get("Time Series (Daily)");

        String symbol = companyOverviewData.get("Symbol");
        String country = companyOverviewData.get("Country");
        String sector = companyOverviewData.get("Sector");

        for (Map.Entry<String, Map<String, String>> entry : timeSeriesDaily.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey());
            if (latestDate == null || !date.isBefore(latestDate)) {
                Map<String, String> dailyData = entry.getValue();

                Stock stockDTO = new Stock();
                stockDTO.setStockId(symbol); //
                stockDTO.setDateTime(date);
                stockDTO.setName(companyOverviewData.get("Name"));
                stockDTO.setPrice(new BigDecimal(dailyData.get("5. adjusted close")));
                stockDTO.setGeographicalRegion(country);
                stockDTO.setIndustrySector(sector);

                stockDTOList.add(stockDTO);
            }
        }

        return stockDTOList;
    }

    // Getters and setters

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGeographicalRegion() {
        return geographicalRegion;
    }

    public void setGeographicalRegion(String geographicalRegion) {
        this.geographicalRegion = geographicalRegion;
    }

    public String getIndustrySector() {
        return industrySector;
    }

    public void setIndustrySector(String industrySector) {
        this.industrySector = industrySector;
    }

}
