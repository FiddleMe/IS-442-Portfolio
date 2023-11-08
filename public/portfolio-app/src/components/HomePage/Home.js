import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import DonutChart from './DonutChart';

import './HomePage.css';
import HistoricalChart from './HistoricalChart';
import { useNavigate } from 'react-router-dom';
import AddStockPopUp from '../AddStock/AddStockPopUp';
import { useLocation } from 'react-router-dom';
import PortfoliosView from './PortfoliosView';
import Spinner from "../Spinner";
const apiUrl = 'http://localhost:8082/api/portfolio';

let data = [
  { value: 10, label: 'Technology' },
  { value: 4, label: 'Finance' },
  { value: 26, label: 'Construction' },
];
let historicalData = [
  { name: '2023-01-01', value: 0.0 },
  { name: '2023-01-02', value: 0.0 },
  { name: '2023-01-03', value: 0.0 },
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
  const [portfolioData, setPortfolioData] = useState(null);
  const [currentPortfolio, setCurrentPortfolio] = useState(null);
  const [currentHistData, setCurrentHistData] = useState([]);
  const [currentGeoData, setCurrentGeoData] = useState([]);
  const [currentIndData, setCurrentIndData] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  const handleRowClick = (portfolio) => {
    setCurrentPortfolio(portfolio);
    console.log(portfolio);
    navigate('/home', { replace: true });
  };
  const fetchHistoricalData = async (interval = 'daily') => {
    try {
      const portfolioId = currentPortfolio.portfolioId;

      const historicalDataResponse = await fetch(
        `http://localhost:8082/api/insights/historical-returns/${interval}/${portfolioId}`
      );
      if (!historicalDataResponse.ok) {
        throw new Error(`HTTP error! Status: ${historicalDataResponse.status}`);
      }
      const historicalData = await historicalDataResponse.json();

      setCurrentHistData(historicalData);
      console.log(historicalData);
    } catch (error) {
      console.error('Error fetching historical data:', error);
    }
  };
  const fetchGeoData = async () => {
    try {
      const portfolioId = currentPortfolio.portfolioId;
      const geoDataResponse = await fetch(
        `http://localhost:8082/api/insights/geo-distribution/${portfolioId}`
      );
      if (!geoDataResponse.ok) {
        throw new Error(`HTTP error! Status: ${geoDataResponse.status}`);
      }
      const geoData = await geoDataResponse.json();

      setCurrentGeoData(geoData.data);
      console.log(geoData);
    } catch (error) {
      console.error('Error fetching historical data:', error);
    }
  };
  const fetchIndData = async () => {
    try {
      const portfolioId = currentPortfolio.portfolioId;
      const indDataResponse = await fetch(
        `http://localhost:8082/api/insights/industry-distribution/${portfolioId}`
      );
      if (!indDataResponse.ok) {
        throw new Error(`HTTP error! Status: ${indDataResponse.status}`);
      }
      const indData = await indDataResponse.json();

      setCurrentIndData(indData.data);
      console.log(indData);
    } catch (error) {
      console.error('Error fetching historical data:', error);
    }
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

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          `http://localhost:8082/api/portfolio/user/${userDetails.userId}`
        );
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();

        let portfolios = await data.data;
        if (portfolios.length === 0) {
          navigate('/create-portfolio');
        }
        var updatedPortfolios = [];
        for (let i = 0; i < portfolios.length; i++) {
          const portfolio = await portfolios[i];
          const portfolioId = await portfolio.portfolioId;

          const insightsData = await fetch(
            `http://localhost:8082/api/insights/insights/${portfolioId}`
          ).then((response) => response.json());
          const updatedInsightsData = insightsData.data;
          let { totalProfitLoss: profitInsightsData, profitLoss: stockInsightsData } =
            await updatedInsightsData;

          const profitInsights = await profitInsightsData;
          const stockInsights = await stockInsightsData;

          const updatedPortfolio = portfolio;
          updatedPortfolio.profitInsights = profitInsights;
          updatedPortfolio.stockInsights = stockInsights;

          updatedPortfolios.push(updatedPortfolio);

          if (i === 0) {
            setCurrentPortfolio(updatedPortfolio);
          }
        }

        setPortfolioData(updatedPortfolios);
        setLoading(false);
      } catch (error) {
        console.error('Error:', error);
        setLoading(false);
      }
    };

    fetchData();
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

  useEffect(() => {
    const fetchData = async () => {
      if (currentPortfolio) {
        await new Promise((resolve) => setTimeout(resolve, 400));
        await fetchGeoData();
        await fetchIndData();
        await fetchHistoricalData();
      }
    };

    fetchData();
  }, [currentPortfolio]);

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
      value: data[key] * 100,
      label: key,
    }));
  }
  function transformIndustrialData(data) {
    return Object.keys(data).map((key) => ({
      value: data[key] * 100,
      label: key.charAt(0).toUpperCase() + key.slice(1).toLowerCase(),
    }));
  }
  function transformHistoricalData(data) {
    return Object.keys(data).map((key) => ({
      value: data[key],
      name: key,
    }));
  }
  console.log(data)

  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar} />

        <div className="col-md p-0">
          <Header name={name} email={email} />
          {loading ? <Spinner /> : <>
            <div className="container mt-5">
              {currentPortfolio !== null && currentPortfolio.stockInsights.length !== 0 ? (
                <>
                  <div className="row mt-2 me-3">
                    <div className="bg-white rounded-3 col-lg-12 col-md-12 col-sm-12 d-flex justify-content-center ms-3 w-100">
                      {currentHistData.length !== 0 &&
                        !isPromise(currentHistData) &&
                        Object.keys(currentHistData).length !== 0 ? (
                        <HistoricalChart
                          title={'Performance'}
                          historicalData={transformHistoricalData(currentHistData)}
                          fetchHistoricalData={fetchHistoricalData}
                          setCurrentHistData={setCurrentHistData}
                        />
                      ) : (
                        <HistoricalChart title={'Performance'} historicalData={historicalData} />
                      )}
                    </div>
                  </div>
                  <div className="row mt-3">
                    <div className="col-lg-6 col-md-6 col-sm-12">
                      {currentIndData.length !== 0 && !isPromise(currentIndData) ? (
                        <DonutChart
                          title={'Industrial Distribution'}
                          data={transformIndustrialData(currentIndData)}
                        />
                      ) : (
                        <DonutChart title={'Industrial Distribution'} data={data} />
                      )}
                    </div>
                    <div className="col-lg-6 col-md-6 col-sm-12">
                      {currentGeoData.length !== 0 && !isPromise(currentGeoData) ? (
                        <DonutChart
                          title={'Geographical Distribution'}
                          data={transformGeographicalData(currentGeoData)}
                        />
                      ) : (
                        <DonutChart title={'Geographical Distribution'} data={geographicalData} />
                      )}
                    </div>
                  </div>
                </>
              ) : (
                <div className="col-12 d-flex justify-content-center align-items-center m-3 wrapper">
                  <div className="border p-3">
                    <p className="text-center">
                      No stocks found in this portfolio. Please add stocks.
                    </p>
                  </div>
                </div>
              )}
            </div>

            <PortfoliosView
              portfolioData={portfolioData}
              currentPortfolio={currentPortfolio}
              handleRowClick={handleRowClick}
            />
          </>
          }
        </div>
      </div>
    </div>
  );
}

export default Home;
