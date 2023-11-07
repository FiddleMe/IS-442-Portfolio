import React, { useRef, useEffect, useState } from 'react';
import { Chart } from 'chart.js/auto';
import { useNavigate } from 'react-router-dom';

function PriceDifferenceChart() {
  const chartRef = useRef();
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [chartData, setChartData] = useState(null);
  const [percentageChange, setPercentageChange] = useState(null);

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  useEffect(() => {
    if (startDate && endDate) {
      // Fetch the price data from your backend based on the selected dates
      // For this example, we'll assume you have fetched the price data
      // Replace this with actual data retrieval logic
      const priceData = [100, 105, 110, 108, 112]; // Replace with actual price data

      // Calculate the percentage change between the two dates
      const change = calculatePercentageChange(startDate, endDate);

      // Update the percentage change state
      setPercentageChange(change);

      // Create chart data with dates and price data
      const dummyChartData = {
        labels: [startDate, endDate],
        datasets: [
          {
            label: 'Price',
            data: priceData, // Replace with actual price data
            borderColor: 'blue',
            fill: false,
          },
        ],
      };

      // Render the new chart
      renderChart(dummyChartData);
    }
  }, [startDate, endDate]);

  const destroyChart = () => {
    const canvas = chartRef.current;
    const ctx = canvas.getContext('2d');

    // Check if a chart already exists and destroy it
    if (ctx.chart) {
      ctx.chart.destroy();
    }
  };

  const renderChart = (data) => {
    if (!data) {
      return;
    }

    const canvas = chartRef.current;
    const ctx = canvas.getContext('2d');

    // Render the new chart
    ctx.chart = new Chart(ctx, {
      type: 'line',
      data: data,
      options: {} // Add any chart options here
    });
  };

  // Function to calculate percentage change between two dates
  const calculatePercentageChange = (startDate, endDate) => {
    // Calculate the percentage change based on the fetched price data
    // For this example, we'll use placeholder values
    const startPrice = 100;
    const endPrice = 112;
    const change = ((endPrice - startPrice) / startPrice) * 100;
    return change;
  };

  return (
    <div>
      <h2>Price Difference Chart</h2>
      <div>
        <label>Start Date: </label>
        <input type="date" value={startDate} onChange={handleStartDateChange} />
      </div>
      <div>
        <label>End Date: </label>
        <input type="date" value={endDate} onChange={handleEndDateChange} />
      </div>
      <canvas ref={chartRef} />
      {percentageChange && (
        <p>Percentage Change: {percentageChange.toFixed(2)}%</p>
      )}
    </div>
  );
}

export default PriceDifferenceChart;
