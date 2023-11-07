import axios from 'axios'; // Import Axios

export const getAllStocks = async () => {
  try {
    const response = await axios.get(`http://localhost:8082/api/stocks`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.status === 200) {
      return response.data;
    } else {
      console.error('Failed to fetch applied role listing IDs');
      throw new Error('Failed to fetch applied role listing IDs');
    }
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
};
