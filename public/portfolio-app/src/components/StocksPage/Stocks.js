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
    navigate('/stocks');
  };


  const fetchStockData = async () => {
    if (searchTerm) {
      setLoading(true);
      const apiKey = 'VFVETDZPXW4IOBLD';
      const apiUrl = `https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=${searchTerm}&interval=5min&apikey=${apiKey}`;

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

            {stockData && stockData['Time Series (5min)'] && (
              <div>
                <p>Stock Name: {searchTerm}</p>
                <p>Stock Price: {parseFloat(stockData['Time Series (5min)'][Object.keys(stockData['Time Series (5min)'])[0]]['1. open']).toFixed(2)}</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchStock;
