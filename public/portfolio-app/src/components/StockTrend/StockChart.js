import React, { useRef, useEffect } from 'react';
import { Chart } from 'chart.js/auto';

const StockChart = ({ data, width, height }) => {
  const chartRef = useRef(null);
  const chartInstance = useRef(null);

  useEffect(() => {
    // Destroy the previous chart if it exists
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    if (data.length > 0 && chartRef.current) {
      const years = data.map((item) => `${item.year}`);
      const percentageDifferences = data.map((item) => item.percentageDifference);

      const backgroundColors = percentageDifferences.map((percentage) =>
        percentage >= 0 ? 'rgba(75, 192, 192, 0.2)' : 'rgba(255, 99, 132, 0.2)'
      );

      const borderColors = percentageDifferences.map((percentage) =>
        percentage >= 0 ? 'rgba(75, 192, 192, 1)' : 'rgba(255, 99, 132, 1)'
      );

      chartInstance.current = new Chart(chartRef.current, {
        type: 'bar',
        data: {
          labels: years,
          datasets: [
            {
              label: 'Percentage Difference',
              backgroundColor: backgroundColors,
              borderColor: borderColors,
              borderWidth: 1,
              data: percentageDifferences,
            },
          ],
        },
        options: {
          responsive: true,
          maintainAspectRatio: true,
        },
      });
    }

    // Ensure the chart instance is destroyed when the component unmounts
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [data]);

  return <canvas ref={chartRef} width={width} height={height} />;
};

export default StockChart;

