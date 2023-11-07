export function transformPortfolioStocks(inputObjects, portfolioId) {
  console.log(inputObjects);
  const transformedObjects = inputObjects.map((obj) => {
    return {
      quantity: obj.quantity,
      date: obj.date,
      portfolioId: portfolioId, // You can customize this value as needed
      stockId: obj.stockId,
    };
  });

  return transformedObjects;
}
