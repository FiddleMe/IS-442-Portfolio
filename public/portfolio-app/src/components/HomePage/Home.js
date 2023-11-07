import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
//import { FaBookOpen } from 'react-icons/fa';  // never used
import DonutChart from './DonutChart';
import { BsPencil, BsPlusLg } from 'react-icons/bs';
import './HomePage.css';
import HistoricalChart from './HistoricalChart';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

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
    console.log(portfolio)
    navigate('/home', { replace: true });
  };
  const [portfolioData, setPortfolioData] = useState(null);
  const [currentPortfolio, setCurrentPortfolio] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkSessionStorage = () => {
      if (sessionStorage.getItem('userData') === null) {
        navigate("/");
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
        console.log(selectedPortfolio)
        const portfolio = portfolioData.find(item => item.name === selectedPortfolio);
        console.log(portfolio)
        setCurrentPortfolio(portfolio);
      }
    }
  }, [loading, portfolioData, location.search]);


  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    const portfolio = portfolioData.find(item => item.name === data);
    setCurrentPortfolio(portfolio);
    navigate('/home', { replace: true });
  };
  const userDetails = sessionStorage.getItem('userData')!=null? JSON.parse(sessionStorage.getItem('userData')): null;
  const name = userDetails !=null ? userDetails.firstName + ' ' + userDetails.lastName : '';
  const email = userDetails !=null ? userDetails.email: '';
  const userId = userDetails !=null ? userDetails.userId: '';
  

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
        <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar}/>

        <div className="col-md p-0">
          <Header name={name} email={email} />

          {currentPortfolio !== null &&
          currentPortfolio !== undefined &&
          currentPortfolio.hasOwnProperty('historicalInsights') &&
          currentPortfolio.stockInsights.length !== 0 ? (
            <div className="m-2 d-flex flex-wrap gap-4">
              {currentPortfolio !== null &&
              currentPortfolio !== undefined &&
              currentPortfolio.hasOwnProperty('historicalInsights') &&
              !isPromise(currentPortfolio.historicalInsights) &&
              currentPortfolio.name !== 'Portfolio A' &&
              currentPortfolio.historicalInsights.length !== 0 &&
              !isPromise(currentPortfolio.historicalInsights) ? (
                <HistoricalChart
                  title={'Performance'}
                  historicalData={currentPortfolio.historicalInsights}
                />
              ) : (
                <HistoricalChart title={'Performance'} historicalData={historicalData} />
              )}

              {currentPortfolio !== null &&
              currentPortfolio !== undefined &&
              !isPromise(currentPortfolio.industryInsights) &&
              currentPortfolio.name !== 'Portfolio A' ? (
                <DonutChart
                  title={'Industrial Distrubution'}
                  data={currentPortfolio.industryInsights}
                />
              ) : (
                <DonutChart title={'Industrial Distrubution'} data={data} />
              )}

              {currentPortfolio !== null &&
              currentPortfolio !== undefined &&
              !isPromise(currentPortfolio.geographicalInsights) &&
              currentPortfolio.name !== 'Portfolio A' ? (
                <DonutChart
                  title={'Geographical Distrubution'}
                  data={currentPortfolio.geographicalInsights}
                />
              ) : (
                <DonutChart title={'Geographical Distrubution'} data={geographicalData} />
              )}
            </div>
          ) : (
            // <div className="d-flex justify-content-center align-items-center p-3">
            //   No stocks found in this portfolio, Please add stocks.
            // </div>
            <div className="d-flex justify-content-center align-items-center m-3 wrapper" >
              <div className="border p-3">
                <p className="text-center">No stocks found in this portfolio. Please add stocks.</p>
              </div>
            </div>
            
          )}
          <div className="">
            <div className="m-3 d-flex flex-wrap gap-4 row">
              <div className="bg-white rounded-3 p-3 col-12 col-md-5">
                <h3 className='py-2 fw-bolder'>Portfolios</h3>
                <table className="table">
                  <thead className="">
                    <tr>
                      <th scope="col" className="text-muted fw-bolder">
                        Name
                      </th>
                      <th scope="col" className="text-muted fw-bolder">
                        Balance
                      </th>
                      <th scope="col" className="text-muted fw-bolder">
                        Change
                      </th>
                    </tr>
                  </thead>
                  {portfolioData !== null && (
                    <tbody>
                      {portfolioData.map((Portfolio) => (
                        <tr
                          key={Portfolio.name}
                          className={Portfolio === currentPortfolio ? 'table-active text-bold' : ''}
                          onClick={() => handleRowClick(Portfolio)}
                          style={{ cursor: 'pointer' }}
                        >
                          <td>{Portfolio.name}</td>
                          <td>${Portfolio.capitalAmount.toLocaleString()}</td>
                          <td
                            className={
                              Portfolio.profitInsights
                                ? Portfolio.profitInsights.totalProfitLossPercentage === 0.0
                                  ? 'text-normal'
                                  : Portfolio.profitInsights.totalProfitLossPercentage < 0
                                  ? 'text-danger'
                                  : 'text-success'
                                : ''
                            }
                          >
                            {
                              Portfolio.profitInsights
                                ? (
                                    Portfolio.profitInsights.totalProfitLossPercentage * 100
                                  ).toFixed(2) + '%'
                                : 'Loading...' // Display a loading message when insights are not available
                            }
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  )}
                </table>
              </div>

              {currentPortfolio !== null && currentPortfolio !== undefined && (
                <div className="bg-white rounded-3 p-3 col-12 col-md-6">
                  <span className="fw-bold">${currentPortfolio.name} </span>
                  <div>
                    <span className="text-secondary">
                      ${currentPortfolio.capitalAmount.toLocaleString()}
                    </span>
                    <div>
                      <h3 className="d-inline fw-bolder">
                        ${currentPortfolio.capitalAmount.toLocaleString()}
                      </h3>
                      <div
                        className={`d-inline fw-bolder mx-2 ${
                          currentPortfolio.profitInsights
                            ? currentPortfolio.profitInsights.totalProfitLossPercentage === 0.0
                              ? 'text-normal'
                              : currentPortfolio.profitInsights.totalProfitLossPercentage < 0
                              ? 'text-danger'
                              : 'text-success'
                            : ''
                        }`}
                      >
                        {
                          currentPortfolio.profitInsights
                            ? (
                                currentPortfolio.profitInsights.totalProfitLossPercentage * 100
                              ).toFixed(2) + '%'
                            : 'Loading...' // Display a loading message when insights are not available
                        }
                      </div>
                      <button className="float-end btn btn-outline-primary buttonFont">
                        <BsPencil className="pb-1 buttonIcon" /> Edit Portfolio
                      </button>
                    </div>
                  </div>
                  <br />

                  <div>
                    <span className="fw-bolder">Stocks</span>
                    <button className="float-end btn btn-outline-primary buttonFont">
                      <BsPlusLg className="pb-1 buttonIcon" /> Add Stocks
                    </button>
                  </div>
                  <br />
                  <table className="table">
                    <thead className="">
                      <tr>
                        <th scope="col" className="text-muted fw-bolder">
                          Name
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Price
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Qty
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Change
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Profit Loss
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Allocation
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      {' '}
                      {currentPortfolio.stockInsights.map((stock, index) => (
                        <tr key={index}>
                          <td>{stock.name}</td>
                          <td>${stock.currentPrice.toFixed(2)}</td>
                          <td>{stock.qty}</td>
                          <td
                            className={`${
                              stock.priceDifference.toFixed(2) === 0.0
                                ? 'text-normal'
                                : stock.priceDifference.toFixed(2) < 0
                                ? 'text-danger'
                                : 'text-success'
                            }`}
                          >
                            {stock.priceDifference.toFixed(2) > 0 ? '+ ' : '- '}
                            {stock.priceDifference.toFixed(2)}
                          </td>
                          <td
                            className={`${
                              (stock.profitLossPercentage * 100).toFixed(2) === 0.0
                                ? 'text-normal'
                                : (stock.profitLossPercentage * 100).toFixed(2) < 0
                                ? 'text-danger'
                                : 'text-success'
                            }`}
                          >
                            {(stock.profitLossPercentage * 100).toFixed(2)}%
                          </td>
                          <td>{(stock.allocation * 100).toFixed(2)}%</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
