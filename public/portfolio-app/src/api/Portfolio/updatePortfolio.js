import axios from 'axios'; // Import Axios

export const updatePortfolio = async (portfolioId, data) => {
  try {
    const url = `http://localhost:8082/api/portfolio/${portfolioId}`;
    const response = await axios.put(url, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    console.log(response);

    if (response.data.status === 200) {
      const responseData = await response.data;
      return responseData.data;
    } else {
      console.error('Failed to create portfolio');
      throw new Error('Failed to create portfolio');
    }
  } catch (error) {
    console.error('Error:', error);
    throw error;
  }
};
