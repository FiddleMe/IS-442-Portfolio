import React, { useState, useEffect } from 'react';
import { Button, OverlayTrigger, Popover } from 'react-bootstrap';
import { BsPencil, BsPlusLg } from 'react-icons/bs';
import AddStockPopUp from '../AddStock/AddStockPopUp';

const PortfoliosView = ({ portfolioData, currentPortfolio, handleRowClick }) => {
  // console.log("from portfolioview", currentPortfolio)
  const [recommendation, setRecommendation] = useState(null);

  const fetchRecommendation = async () => {
    if (currentPortfolio) {
      const response = await fetch(
        `http://localhost:8082/api/insights/redistribution/${currentPortfolio.portfolioId}`
      );
      const data = await response.json();
      setRecommendation(data.data);
    }
  };

  useEffect(() => {
    fetchRecommendation();
  }, [currentPortfolio]);

  const recommendationPopover = (
    <Popover id="recommendation-popover">
      <Popover.Header as="h3">Recommendation</Popover.Header>
      <Popover.Body>
        {recommendation &&
          Object.entries(recommendation).map(([sector, data]) => (
            <div key={sector} className="mb-3">
              <h5 className="mb-1">{sector.charAt(0).toUpperCase() + sector.slice(1).toLowerCase()}</h5>
              <p className="mb-1">
                <strong>Current:</strong> {data.current}%
              </p>
              {data.decision === 'same' ? (
                <p className="mb-1 text-primary">
                  Your allocation is already optimal. No changes are recommended.
                </p>
              ) : (
                <p className="mb-1">
                  We recommend you to <span className={data.decision === 'sell' ? 'text-danger' : 'text-success'}>{data.decision.toUpperCase()}</span> stock within {sector.charAt(0).toUpperCase() + sector.slice(1).toLowerCase()} to rebalance it to initial {data.initial}%
                </p>
              )}
            </div>
          ))}
      </Popover.Body>
    </Popover>
  );

  return (
    <div className="m-3 d-flex flex-wrap gap-4 row">
      <div className="bg-white rounded-3 p-3 col-12 col-md-5">
        <h3 className="py-2 fw-bolder">Portfolios</h3>
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
                    {Portfolio.profitInsights
                      ? (Portfolio.profitInsights.totalProfitLossPercentage * 100).toFixed(2) + '%'
                      : 'Loading...'}
                  </td>
                </tr>
              ))}
            </tbody>
          )}
        </table>
      </div>

      {currentPortfolio !== null && currentPortfolio !== undefined && (
        <div className="bg-white rounded-3 p-3 col-12 col-md-6">
          <span className="fw-bold">{currentPortfolio.name} </span>
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
                {currentPortfolio.profitInsights
                  ? (currentPortfolio.profitInsights.totalProfitLossPercentage * 100).toFixed(2) +
                    '%'
                  : 'Loading...'}
              </div>
              <OverlayTrigger trigger="click" placement="bottom" overlay={recommendationPopover}>
                <button className="float-end btn btn-outline-primary buttonFont">
                  <BsPencil className="pb-1 buttonIcon" /> Recommendation
                </button>
              </OverlayTrigger>
            </div>
          </div>
          <br />

          <div>
            <span className="fw-bolder">Stocks</span>
            <AddStockPopUp portfolio={currentPortfolio} />
          </div>
          <br />
          <div className="overflow-scroll">
            <table className="table ">
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
        </div>
      )}
    </div>
  );
};

export default PortfoliosView;
