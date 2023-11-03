// Function to filter and transform the response data
function filterLatestStocks(data) {
    const stocksData = JSON.parse(data);
    const groupedStocks = {};
  
    stocksData.forEach((stock) => {
      const stockId = stock.stockId;
      if (!(stockId in groupedStocks) || stock.date > groupedStocks[stockId].date) {
        groupedStocks[stockId] = stock;
      }
    });
  
    return Object.values(groupedStocks);
  }