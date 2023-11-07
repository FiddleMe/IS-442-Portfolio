import axios from 'axios'; // Import Axios

export const createPortfolio = async (reqBody) => {
  try {
    const response = await axios.post('http://localhost:8082/api/portfolio', {
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(reqBody),
    });

    if (response.status === 201) {
      const responseData = await response.data.json(); // Parse the response body as JSON
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
