import axios from 'axios'; // Import Axios

export const createPortfolio = async (data) => {
  try {
    const response = await axios.post('http://localhost:8082/api/portfolio', data, {
      headers: {
        'Content-Type': 'application/json',
      },
    
    });

    console.log(response);

    if (response.status === 201) {
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
