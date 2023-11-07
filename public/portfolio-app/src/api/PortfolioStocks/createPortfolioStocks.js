import axios from 'axios'; // Import Axios

export const createPortfolioStocks = async (data) => {
  try {
    const response = await axios.post(`http://localhost:8082/api/portfoliostocks`, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    console.log(response);
    if (response.status === 200) {
      return response.data.data;
    } else {
      console.error('Failed to fetch applied role listing IDs');
      throw new Error('Failed to fetch applied role listing IDs');
    }
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
};
