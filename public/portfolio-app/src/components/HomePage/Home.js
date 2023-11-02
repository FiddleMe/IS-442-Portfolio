import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { FaBookOpen } from 'react-icons/fa';
import DonutChart from './DonutChart';
import { BsPencil, BsPlusLg } from 'react-icons/bs';

import HistoricalChart from './HistoricalChart';

const apiUrl = 'http://localhost:8082/api/portfolio';

let data = [
  { value: 10, label: 'Technology' },
  { value: 4, label: 'Finance' },
  { value: 26, label: 'Construction' },
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
  const handleRowClick = (portfolio) => {
    setCurrentPortfolio(portfolio);
  };
  const [portfolioData, setPortfolioData] = useState(null);
  const [currentPortfolio, setCurrentPortfolio] = useState(null);

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
        let portfolios = data.data;
        // console.log(data.data);
        const portfolioPromises = portfolios.map(async (portfolio) => {
          const portfolioId = portfolio.portfolioId;

          // Fetch all required insights data concurrently
          const [
            profitInsightsData,
            historicalInsightsData,
            industryInsightsData,
            geographicalInsightsData,
            stockInsightsData,
          ] = await Promise.all([
            fetch(`http://localhost:8082/api/insights/total-profit-loss/${portfolioId}`).then(
              (response) => response.json()
            ),
            fetch(
              `http://localhost:8082/api/insights/historical-returns/daily/${portfolioId}`
            ).then((response) => response.json()),
            fetch(`http://localhost:8082/api/insights/industry-distribution/${portfolioId}`).then(
              (response) => response.json()
            ),
            fetch(`http://localhost:8082/api/insights/geo-distribution/${portfolioId}`).then(
              (response) => response.json()
            ),
            fetch(`http://localhost:8082/api/insights/profit-loss/${portfolioId}`).then(
              (response) => response.json()
            ),
          ]);
          console.log('Fetch requests completed');

          const profitInsights = profitInsightsData.data;
          const historicalInsights = transformHistoricalData(historicalInsightsData);
          // console.log('historicaldata');
          // console.log(historicalInsights);
          const industryInsights = transformData(industryInsightsData.data);
          const geographicalInsights = transformData(geographicalInsightsData.data);
          const stockInsights = stockInsightsData.data;

          const updatedPortfolio = {
            ...portfolio,
            profitInsights,
            historicalInsights,
            industryInsights,
            geographicalInsights,
            stockInsights,
          };

          return updatedPortfolio;
        });

        // Wait for all portfolio promises to resolve and set the portfolio data
        const updatedPortfolios = await Promise.all(portfolioPromises);
        console.log('upadetport: ', updatedPortfolios);

        // Set the portfolio data after all promises have resolved
        setPortfolioData(updatedPortfolios);
        setCurrentPortfolio(updatedPortfolios[0]);
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchData() // Call the fetchData function when the component mounts
      .then((portfolios) => {
        // Set currentPortfolio to the first portfolio from portfolioData
        if (portfolios !== undefined) {
          setCurrentPortfolio(portfolios[0]);
        }
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  }, []);

  useEffect(() => {
    console.log('Portfolio data updated:', portfolioData);
    console.log('Current portfolio updated:', currentPortfolio);
  }, [portfolioData, currentPortfolio]);

  const subPages = [
    { icon: FaBookOpen, title: 'Portfolio 1' },
    { icon: FaBookOpen, title: 'Portfolio 2' },
  ];
  const userDetails = JSON.parse(sessionStorage.getItem('userData'));
  // console.log(userDetails);
  const name = userDetails.firstName + ' ' + userDetails.lastName;
  const email = userDetails.email;

  const currentPage = 'Portfolio 1';
  // console.log(currentPortfolio);
  function isPromise(p) {
    return p && Object.prototype.toString.call(p) === '[object Promise]';
  }

  function transformData(data) {
    return Object.keys(data).map((key) => ({
      value: data[key], // Multiplying by 10 for demonstration
      label: key,
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
        <Sidebar subPages={subPages} currentPage={currentPage} />
        {currentPortfolio !== null && (
          <div className="col-md p-0">
            <Header name={name} email={email} />
            <div className="m-2 d-flex flex-wrap gap-4">
              {currentPortfolio.hasOwnProperty('historicalInsights') &&
              currentPortfolio.name !== 'Portfolio A' &&
              currentPortfolio.historicalInsights.length !== 0 &&
              !isPromise(currentPortfolio.historicalInsights) ? (
                <HistoricalChart
                  title={'Perforamnce'}
                  historicalData={currentPortfolio.historicalInsights}
                />
              ) : (
                <div>Loading...</div>
              )}

              {!isPromise(currentPortfolio.industryInsights) &&
              currentPortfolio.name !== 'Portfolio A' ? (
                <DonutChart
                  title={'Industrial Distrubution'}
                  data={currentPortfolio.industryInsights}
                />
              ) : (
                <div>Loading...</div>
              )}

              {!isPromise(currentPortfolio.geographicalInsights) &&
              currentPortfolio.name !== 'Portfolio A' ? (
                <DonutChart
                  title={'Geographical Distrubution'}
                  data={currentPortfolio.geographicalInsights}
                />
              ) : null}
            </div>
            <div className="">
              <div className="m-2 d-flex flex-wrap gap-4 row">
                <div className="bg-white rounded-3 p-3 col-12 col-md-5">
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
                            className={
                              Portfolio === currentPortfolio ? 'table-active text-bold' : ''
                            }
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

                <div className="bg-white rounded-3 p-3 col-12 col-md-6 ">
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
                          Change
                        </th>
                        <th scope="col" className="text-muted fw-bolder">
                          Profit Loss Percentage
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      {' '}
                      {currentPortfolio.stockInsights.map((stock, index) => (
                        <tr key={index}>
                          <td>{stock.name}</td>
                          <td>${stock.currentPrice.toFixed(2)}</td>
                          <td>${stock.priceDifference.toFixed(2)}</td>
                          <td>{(stock.profitLossPercentage * 100).toFixed(2)}%</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default Home;
