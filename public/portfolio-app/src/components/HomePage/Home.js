import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import DonutChart from './DonutChart';

import './HomePage.css';
import HistoricalChart from './HistoricalChart';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import PortfoliosView from './PortfoliosView';

const apiUrl = 'http://localhost:8082/api/portfolio';

let data = [
  { value: 10, label: 'Technology' },
  { value: 4, label: 'Finance' },
  { value: 26, label: 'Construction' },
];
let historicalData = [
  { name: '2023-01-01', value: 100.0 },
  { name: '2023-01-02', value: 150.0 },
  { name: '2023-01-03', value: 120.0 },
  { name: '2023-01-04', value: 0.0 },
  // Add more data points with date and value
];
let geographicalData = [
  { value: 10, label: 'North America' },
  { value: 8, label: 'Europe' },
  { value: 5, label: 'Asia' },
  { value: 4, label: 'South America' },
  { value: 2, label: 'Africa' },
  { value: 1, label: 'Australia' },
];

function Home() {
  const navigate = useNavigate();
  const location = useLocation();

  const handleRowClick = (portfolio) => {
    setCurrentPortfolio(portfolio);
    console.log(portfolio);
    navigate('/home', { replace: true });
  };
  const [portfolioData, setPortfolioData] = useState(null);
  const [currentPortfolio, setCurrentPortfolio] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkSessionStorage = () => {
      if (sessionStorage.getItem('userData') === null) {
        navigate('/');
        return;
      }
    };

    checkSessionStorage();
  }, []);

  useEffect(() => {
    console.log('Home component rendered');
    // Function to fetch data from the API
    const fetchData = async () => {
      try {
        const response = await fetch(apiUrl + `/user/${userDetails.userId}`);
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
        console.log('safsafs', data);
        let portfolios = data.data;
        if (portfolios.length === 0) {
          navigate('/create-portfolio');
        }
        const updatedPortfolios = [];
        // console.log(data.data);
        for (let i = 0; i < portfolios.length; i++) {
          const portfolio = portfolios[i];
          const portfolioId = portfolio.portfolioId;

          // Fetch all required insights data sequentially
          const profitInsightsData = await fetch(
            `http://localhost:8082/api/insights/total-profit-loss/${portfolioId}`
          ).then((response) => response.json());

          const historicalInsightsData = await fetch(
            `http://localhost:8082/api/insights/historical-returns/daily/${portfolioId}`
          ).then((response) => response.json());

          const industryInsightsData = await fetch(
            `http://localhost:8082/api/insights/industry-distribution/${portfolioId}`
          ).then((response) => response.json());

          const geographicalInsightsData = await fetch(
            `http://localhost:8082/api/insights/geo-distribution/${portfolioId}`
          ).then((response) => response.json());

          const stockInsightsData = await fetch(
            `http://localhost:8082/api/insights/profit-loss/${portfolioId}`
          ).then((response) => response.json());

          const profitInsights = profitInsightsData.data;
          const historicalInsights = transformHistoricalData(historicalInsightsData);
          const industryInsights = transformIndustrialData(industryInsightsData.data);
          console.log(industryInsights);
          const geographicalInsights = transformGeographicalData(geographicalInsightsData.data);
          const stockInsights = stockInsightsData.data;

          const updatedPortfolio = await {
            ...portfolio,
            profitInsights,
            historicalInsights,
            industryInsights,
            geographicalInsights,
            stockInsights,
          };

          updatedPortfolios.push(updatedPortfolio);
          if (i === 0) {
            setCurrentPortfolio(updatedPortfolio);
          }
          await new Promise((resolve) => setTimeout(resolve, 1000));
        }
        console.log('upadetport: ', updatedPortfolios);

        // Set the portfolio data after all promises have resolved
        setPortfolioData(updatedPortfolios);
        setLoading(false);
      } catch (error) {
        console.error('Error:', error);
        setLoading(false);
      }
    };

    fetchData(); // Call the fetchData function when the component mounts
  }, []);

  useEffect(() => {
    console.log('Portfolio data updated:', portfolioData);
    console.log('Current portfolio updated:', currentPortfolio);
  }, [portfolioData, currentPortfolio]);

  useEffect(() => {
    if (!loading) {
      console.log('Data loaded:', portfolioData);
      const searchParams = new URLSearchParams(location.search);
      const selectedPortfolio = searchParams.get('selectedPortfolio');
      if (selectedPortfolio) {
        console.log(selectedPortfolio);
        const portfolio = portfolioData.find((item) => item.name === selectedPortfolio);
        console.log(portfolio);
        setCurrentPortfolio(portfolio);
      }
    }
  }, [loading, portfolioData, location.search]);

  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    const portfolio = portfolioData.find((item) => item.name === data);
    setCurrentPortfolio(portfolio);
    navigate('/home', { replace: true });
  };
  const userDetails =
    sessionStorage.getItem('userData') != null
      ? JSON.parse(sessionStorage.getItem('userData'))
      : null;
  const name = userDetails != null ? userDetails.firstName + ' ' + userDetails.lastName : '';
  const email = userDetails != null ? userDetails.email : '';
  const userId = userDetails != null ? userDetails.userId : '';

  const currentPage = 'Home';
  function isPromise(p) {
    return p && Object.prototype.toString.call(p) === '[object Promise]';
  }

  function transformGeographicalData(data) {
    return Object.keys(data).map((key) => ({
      value: data[key], // Multiplying by 10 for demonstration
      label: key,
    }));
  }
  function transformIndustrialData(data) {
    return Object.keys(data).map((key) => ({
      value: data[key], // Multiplying by 10 for demonstration
      label: key.charAt(0).toUpperCase() + key.slice(1).toLowerCase(),
    }));
  }
  function transformHistoricalData(data) {
    // console.log(data);
    const ret = Object.keys(data).map((key) => ({
      value: data[key], // Multiplying by 10 for demonstration
      name: key,
    }));
    // console.log(ret);
    return ret;
  }

  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar} />

        <div className="col-md p-0">
          <Header name={name} email={email} />

          {currentPortfolio !== null &&
          currentPortfolio !== undefined &&
          currentPortfolio.hasOwnProperty('historicalInsights') &&
          currentPortfolio.stockInsights.length !== 0 ? (
            <div className="m-2 d-flex flex-wrap gap-4">
              {!isPromise(currentPortfolio.historicalInsights) &&
              currentPortfolio.historicalInsights.length !== 0 ? (
                <HistoricalChart
                  title={'Performance'}
                  historicalData={currentPortfolio.historicalInsights}
                />
              ) : (
                <HistoricalChart title={'Performance'} historicalData={historicalData} />
              )}

              {!isPromise(currentPortfolio.industryInsights) ? (
                <DonutChart
                  title={'Industrial Distrubution'}
                  data={currentPortfolio.industryInsights}
                />
              ) : (
                <DonutChart title={'Industrial Distrubution'} data={data} />
              )}

              {!isPromise(currentPortfolio.geographicalInsights) ? (
                <DonutChart
                  title={'Geographical Distrubution'}
                  data={currentPortfolio.geographicalInsights}
                />
              ) : (
                <DonutChart title={'Geographical Distrubution'} data={geographicalData} />
              )}
            </div>
          ) : (
            <div className="d-flex justify-content-center align-items-center m-3 wrapper">
              <div className="border p-3">
                <p className="text-center">No stocks found in this portfolio. Please add stocks.</p>
              </div>
            </div>
          )}
          <PortfoliosView
            portfolioData={portfolioData}
            currentPortfolio={currentPortfolio}
            handleRowClick={handleRowClick}
          />
        </div>
      </div>
    </div>
  );
}

export default Home;
