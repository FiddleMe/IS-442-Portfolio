import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';
//import './Stocks.css';

function SearchStock() {
  const userDetails =
    sessionStorage.getItem('userData') != null
      ? JSON.parse(sessionStorage.getItem('userData'))
      : null;
  const name =
    userDetails != null ? userDetails.firstName + ' ' + userDetails.lastName : '';
  const email = userDetails != null ? userDetails.email : '';
  const userId = userDetails != null ? userDetails.userId : '';

  const navigate = useNavigate();
  const currentPage = 'Stocks';
  const stockName = 'Search Stocks';

  const [stockData, setStockData] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(false);

  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    navigate('/home?selectedPortfolio=' + data);
  };
  
  // Prevent user for entering the page with going through login
  useEffect(() => {
    const checkSessionStorage = () => {
      if (sessionStorage.getItem('userData') === null) {
        navigate('/');
        return;
      }
    };

    checkSessionStorage();
  }, []);


  // fetch stock price (DAILY)
  const fetchStockData = async () => {
    if (searchTerm) {
      setLoading(true);
      const apiKey = 'VFVETDZPXW4IOBLD';
      const apiUrl = `https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=${searchTerm}&apikey=${apiKey}`;

      try {
        const response = await fetch(apiUrl);
        const data = await response.json();
        setStockData(data);
        setLoading(false);
      } 
      catch (error) {
        console.error('Error fetching stock data:', error);
        setStockData(null);
        setLoading(false);
      }
    }
  };

  useEffect(() => {
    fetchStockData();
  }, [searchTerm]);


  // fetch stock price in the past year
  // const [percentageChange, setPercentageChange] = useState(null);

  // const calculatePercentageChange = () => {
  //   if (stockData && stockData['Time Series (Daily)']) {
  //     const dailyData = stockData['Time Series (Daily)'];
  //     const dates = Object.keys(dailyData);
  //     const latestDate = dates[0];
  //     const oneYearAgo = dates[251]; // Approximately one year ago

  //     const latestPrice = parseFloat(dailyData[latestDate]['4. close']);
  //     const oneYearAgoPrice = parseFloat(dailyData[oneYearAgo]['4. close']);

  //     const change = latestPrice - oneYearAgoPrice;
  //     const percentageChange = (change / oneYearAgoPrice) * 100;
  //     setPercentageChange(percentageChange.toFixed(2));
  //   }
  // };

  // useEffect(() => {
  //   calculatePercentageChange();
  // }, [stockData]);


 


  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar
          userId={userId}
          currentPage={currentPage}
          onDataToParent={handleDataFromSidebar}
        />
        <div className="col-md p-0">
          <Header name={name} email={email} />

          <div className="px-5">
            <h2>{stockName}</h2>
            <br />
            <br />

            <div className="col-md-6">
              <div className="form-group">
                <input
                  type="text"
                  className="form-control rounded-3"
                  id="stockName"
                  placeholder="Enter stock name"
                  style={{ width: '200%' }}
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />               
              </div>
            </div>

            <br />

            {loading && <p>Loading...</p>}

            {stockData && stockData['Time Series (Daily)'] && (
              <div>
                <p>Stock Name: {searchTerm}</p>
                <p>
                  Stock Price: $
                  {parseFloat(stockData['Time Series (Daily)'][Object.keys(stockData['Time Series (Daily)'])[0]]['5. adjusted close']).toFixed(2)}
                </p>
              </div>
            )}

          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchStock;
