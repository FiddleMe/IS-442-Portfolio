import React from 'react';
import { BsPencil, BsPlusLg } from 'react-icons/bs';
import AddStockPopUp from '../AddStock/AddStockPopUp';

const PortfoliosView = ({ portfolioData, currentPortfolio, handleRowClick }) => {
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
              <button className="float-end btn btn-outline-primary buttonFont">
                <BsPencil className="pb-1 buttonIcon" /> Edit Portfolio
              </button>
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
