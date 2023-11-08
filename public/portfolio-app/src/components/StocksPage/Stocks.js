import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';
import './Stocks.css';
import StockChart from './StockChart'; // Import StockChart


function Stock() {
  const userDetails =
    sessionStorage.getItem('userData') != null
      ? JSON.parse(sessionStorage.getItem('userData'))
      : null;
  const name =
    userDetails != null ? userDetails.firstName + ' ' + userDetails.lastName : '';
  const email = userDetails != null ? userDetails.email : '';
  const userId = userDetails != null ? userDetails.userId : '';

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

  const navigate = useNavigate();
  const currentPage = 'Stocks';
  const stockName = 'Search Stocks';

  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    navigate('/home?selectedPortfolio=' + data);
  };

  // fetch DAILY stock price 
  const [stockData, setStockData] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchStockData = async () => {
    if (searchTerm) {
      setLoading(true);
      const apiKey = 'VFVETDZPXW4IOBLD';
      const outputSize = 'full'; // or 'compact'
      const apiUrl = `https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=${searchTerm}&outputsize=${outputSize}&apikey=${apiKey}`;

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


  // fetch percentage change in stock price
  const [percentageChangeYear, setPercentageChangeYear] = useState(null);
  const [percentageChangeMonth, setPercentageChangeMonth] = useState(null);
  const [percentageChange7Days, setPercentageChange7Days] = useState(null);
  const [percentageChangeToday, setPercentageChangeToday] = useState(null);

  const calculatePercentageChange = () => {
    if (stockData && stockData['Time Series (Daily)']) {
      const dailyData = stockData['Time Series (Daily)'];
      const dates = Object.keys(dailyData);
  
      // Check if there are at least 252 dates (today and approximately one year ago)
      if (dates.length >= 252) {
        const latestDate = dates[0];
        const oneYearAgo = dates[251]; // Approximately one year ago
  
        if (dailyData[latestDate] && dailyData[oneYearAgo]) {
          const latestPrice = parseFloat(dailyData[latestDate]['4. close']);
          const oneYearAgoPrice = parseFloat(dailyData[oneYearAgo]['4. close']);
          const yearChange = latestPrice - oneYearAgoPrice;
          const percentageChangeYear = ((yearChange / oneYearAgoPrice) * 100).toFixed(2);
          setPercentageChangeYear(percentageChangeYear);
        }
      }

      // Check if there are at least 22 dates (today and approximately one month ago)
      if (dates.length >= 22) {
        const today = dates[0];
        const oneMonthAgo = dates[21]; // Approximately one month ago

        if (dailyData[today] && dailyData[oneMonthAgo]) {
          const todayPrice = parseFloat(dailyData[today]['4. close']);
          const oneMonthAgoPrice = parseFloat(dailyData[oneMonthAgo]['4. close']);
          const change = todayPrice - oneMonthAgoPrice;
          const percentageChangeMonth = ((change / oneMonthAgoPrice) * 100).toFixed(2);
          setPercentageChangeMonth(percentageChangeMonth);
        }
      }

      // Calculate the percentage change over 7 days
      if (dates.length >= 8) {
        const latestDate = dates[0];
        const sevenDaysAgo = dates[7]; // Approximately seven days ago
  
        if (dailyData[latestDate] && dailyData[sevenDaysAgo]) {
          const latestPrice = parseFloat(dailyData[latestDate]['4. close']);
          const sevenDaysAgoPrice = parseFloat(dailyData[sevenDaysAgo]['4. close']);
          const change = latestPrice - sevenDaysAgoPrice;
          const percentageChange = ((change / sevenDaysAgoPrice) * 100).toFixed(2);
          setPercentageChange7Days(percentageChange);
        }
      }

      // Calculate the percentage change for today
      if (dates.length >= 1) {
        const today = dates[0];
        const latestDate = dates[0];
        const previousDate = dates[1];

        if (dailyData[today] && dailyData[previousDate]) {
          const todayPrice = parseFloat(dailyData[today]['4. close']);
          const previousPrice = parseFloat(dailyData[previousDate]['4. close']);
          const todayChange = todayPrice - previousPrice;
          const percentageChangeToday = ((todayChange / previousPrice) * 100).toFixed(2);
          setPercentageChangeToday(percentageChangeToday);
        }
      }
    }
  };
  
  useEffect(() => {
    calculatePercentageChange();
  }, [stockData]);
  

  // get color class based on percentage change
  const getColorClass = (percentageChange) => {
    if (percentageChange > 0) {
      return 'green-text';
    } else if (percentageChange < 0) {
      return 'red-text';
    }
    // If percentageChange is zero, you can return a different class or use the default color.
    // For example, you can return 'black-text' for zero.
  };


  // fetch stock search results
  const [searchResults, setSearchResults] = useState([]);
  const [loadingSearch, setLoadingSearch] = useState(false);

  const fetchStockSearchResults = async () => {
    if (searchTerm) {
      setLoadingSearch(true);
      const apiKey = 'KTFKSXGNFXDBMF6M'; 
      try {
        const response = await fetch(`https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=${searchTerm}&apikey=${apiKey}`);
        const data = await response.json();
        if (data.bestMatches) {
          setSearchResults(data.bestMatches);
        } else {
          setSearchResults([]);
        }
        setLoadingSearch(false);
      } catch (error) {
        console.error('Error fetching search results:', error);
        setLoadingSearch(false);
      }
    }
  };

  useEffect(() => {
    fetchStockSearchResults();
  }, [searchTerm]);
  
  
  // fetch market cap
  // const [marketCap, setMarketCap] = useState(null);

  // const fetchMarketCap = async () => {
  //   if (searchTerm) {
  //     const apiKey = 'VFVETDZPXW4IOBLD'; // Replace with your API key
  //     try {
  //       const response = await fetch(`https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=${searchTerm}&apikey=${apiKey}`);
  //       const data = await response.json();
  //       if (data.marketCap) {
  //         setMarketCap(data.marketCap);
  //       } else {
  //         setMarketCap(null); // Set to null if market cap data is not available
  //       }
  //     } catch (error) {
  //       console.error('Error fetching market cap:', error);
  //       setMarketCap(null);
  //     }
  //   }
  // };

  // useEffect(() => {
  //   fetchMarketCap();
  // }, [searchTerm]);
  
  


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
            <h2 className='pt-3'>{stockName}</h2>
            <br />
            <br />

            <div className="col-md-12">
              <div className="form-group">
                <input
                  type="text"
                  className="form-control rounded-3"
                  id="stockName"
                  placeholder="Enter stock name"
                  style={{ width: '100%' }}
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />               
              </div>
            </div>

            <br />

            {loadingSearch && <p>Loading search results...</p>}

            {searchResults.length > 0 && (
              <div>
                <p>* Stock Name - Full Company Name *</p>
                <ul>
                  {searchResults.map((result) => (
                    <li key={result['1. symbol']}>
                      {result['1. symbol']} - {result['2. name']}
                    </li>
                  ))}
                </ul>
              </div>
            )}

            <br />

            {loading && <p>Loading...</p>}

            {stockData && stockData['Time Series (Daily)'] && (
              <div>
                <p><b>Stock:</b> {searchTerm} - {searchResults.length > 0 && searchResults[0]['2. name']}</p>
                {/* {marketCap !== null && (
                  <p>
                    Market Cap <b>:</b> {marketCap}
                  </p>
                )} */}
                <p>
                <b>Latest Stock Price:</b> $
                  {parseFloat(stockData['Time Series (Daily)'][Object.keys(stockData['Time Series (Daily)'])[0]]['5. adjusted close']).toFixed(2)}
                </p>
                <p><b>Latest Date:</b> {Object.keys(stockData['Time Series (Daily)'])[0]}</p>
                
                <br />

                <p>
                  <b>Percentage Change (1 year):</b> {' '}
                  {percentageChangeYear !== null ? (
                    <span className={getColorClass(percentageChangeYear)}>
                      {percentageChangeYear > 0 ? `+${percentageChangeYear}` : percentageChangeYear}%
                    </span>
                  ) : (
                    'Not enough data for 1 year'
                  )}
                </p>

                <p>
                  <b>Percentage Change (30 days):</b> {' '}
                  {percentageChangeMonth !== null ? (
                    <span className={getColorClass(percentageChangeMonth)}>
                      {percentageChangeMonth > 0 ? `+${percentageChangeMonth}` : percentageChangeMonth}%
                    </span>
                  ) : (
                    'Not enough data for 30 days'
                  )}
                </p>

                <p>
                  <b>Percentage Change (7 days):</b> {' '}
                  {percentageChange7Days !== null ? (
                    <span className={getColorClass(percentageChange7Days)}>
                      {percentageChange7Days > 0 ? `+${percentageChange7Days}` : percentageChange7Days}%
                    </span>
                  ) : (
                    'Not enough data for 7 days'
                  )}
                </p>

                <p>
                  <b>Percentage Change (Today):</b> {' '}
                  {percentageChangeToday !== null ? (
                    <span className={getColorClass(percentageChangeToday)}>
                      {percentageChangeToday > 0 ? `+${percentageChangeToday}` : percentageChangeToday}%
                    </span>
                  ) : (
                    'Not enough data for Today'
                  )}
                </p>

              </div>
            )}

            <br />
            <br />

            {/* Add the StockChart component */}
            {searchTerm && (
              <StockChart
                stockSymbol={searchTerm}
                stockData={stockData}
              />
            )}

          </div>
        </div>
      </div>
    </div>
  );
}

export default Stock;
