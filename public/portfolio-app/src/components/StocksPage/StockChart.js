import React, { useEffect, useRef, useState } from 'react';
import Chart from 'chart.js/auto';

function StockChart({ stockSymbol, stockData }) {
  const chartRef = useRef(null);
  const [startYear, setStartYear] = useState('2018'); // Initial start year
  const [endYear, setEndYear] = useState('2023'); // Initial end year

  const handleYearChange = () => {
    // Handle year range change
    // You can fetch data from the selected year range here
    displayStockChart(startYear, endYear);
  };

  useEffect(() => {
    displayStockChart(startYear, endYear);
  }, [stockData, stockSymbol, startYear, endYear]);

  // Function to display the stock chart based on the selected year range
  const displayStockChart = (start, end) => {
    if (!stockData || !stockData['Time Series (Daily)']) {
      return;
    }

    const dailyData = stockData['Time Series (Daily)'];
    const dates = Object.keys(dailyData);

    if (dates.length < 2) {
      return;
    }

    // Extract historical data for the selected year range
    const startDate = new Date(`${start}-01-01`);
    const endDate = new Date(`${end}-12-31`);

    const historicalData = dates
      .filter((date) => {
        const dateObj = new Date(date);
        return dateObj >= startDate && dateObj <= endDate;
      })
      .map((date) => ({
        date,
        price: parseFloat(dailyData[date]['4. close']),
      }))
      .reverse(); // Reverse the data to display the most recent on the left

    let dateLabels = historicalData.map((data) => data.date);
    let priceData = historicalData.map((data) => data.price);

    // Check if a chart instance with ID 'stockChart' already exists and destroy it
    if (Chart.getChart(chartRef.current)) {
      Chart.getChart(chartRef.current).destroy();
    }

    // Create the new chart
    const ctx = chartRef.current.getContext('2d');
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: dateLabels,
        datasets: [
          {
            label: `Stock Price for ${stockSymbol}`,
            data: priceData,
            borderColor: 'rgba(75, 192, 192, 1)', // Customize chart color
            fill: false,
          },
        ],
      },
      options: {
        scales: {
          x: {
            title: {
              display: true,
              text: 'Date',
            },
          },
          y: {
            title: {
              display: true,
              text: 'Price',
            },
          },
        },
      },
    });
  };

  return (
    <div>
      <div>
        <label htmlFor="startYear">Start Year:</label>
        <input
          type="number"
          id="startYear"
          value={startYear}
          onChange={(e) => setStartYear(e.target.value)}
          onBlur={handleYearChange}
        />
      </div>
      <div>
        <label htmlFor="endYear">End Year: </label>
        <input
          type="number"
          id="endYear"
          value={endYear}
          onChange={(e) => setEndYear(e.target.value)}
          onBlur={handleYearChange}
        />
      </div>
      <canvas ref={chartRef} width="400" height="200"></canvas>
    </div>
  );
}

export default StockChart;
