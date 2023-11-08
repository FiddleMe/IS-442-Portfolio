import React, { useState } from 'react';
import { BsPencil, BsPlusLg } from 'react-icons/bs';
import AddStockPopUp from '../AddStock/AddStockPopUp';
import './HomePage.css';

function Portfolios({ PortfolioData }) {
  const [currentPortfolio, setCurrentPortfolio] = useState(PortfolioData[0]);

  const handleRowClick = (portfolio) => {
    setCurrentPortfolio(portfolio);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6">
          <div className="bg-white rounded-3 p-3">
            <table className="table">
              <thead>
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
              <tbody>
                {PortfolioData.map((Portfolio) => (
                  <tr
                    key={Portfolio.name}
                    className={Portfolio === currentPortfolio ? 'table-active text-bold' : ''}
                    onClick={() => handleRowClick(Portfolio)}
                    style={{ cursor: 'pointer' }}
                  >
                    <td>{Portfolio.name}</td>
                    <td>${Portfolio.balance.toLocaleString()}</td>
                    <td className={Portfolio.change < 0 ? 'text-danger' : 'text-success'}>
                      {((Portfolio.change / Portfolio.capitalAmount) * 100).toFixed(2)}%
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="col-md-6">
          <div className="bg-white rounded-3 p-3">
            <div>
              <span className="text-secondary">Portfolio Balance</span>
              <div>
                <h3 className="d-inline fw-bolder">
                  ${currentPortfolio.capitalAmount.toLocaleString()}
                </h3>
                <div
                  className={`d-inline fw-bolder mx-2 ${
                    currentPortfolio.change < 0 ? 'text-danger' : 'text-success'
                  }`}
                >
                  {((currentPortfolio.change / currentPortfolio.capitalAmount) * 100).toFixed(2)}%
                </div>
                <button className="float-end btn btn-outline-primary buttonFont">
                  <BsPencil className="pb-1 buttonIcon" /> Edit Portfolio
                </button>
              </div>
            </div>
            <br />

            <div>
              <AddStockPopUp className="float-end btn btn-outline-primary buttonFont pb-1" />
            </div>
            <br />
            <table className="table">
              <thead>
                <tr>
                  <th scope="col" className="text-muted fw-bolder">
                    Name
                  </th>
                  <th scope="col" className="text-muted fw-bolder">
                    Price
                  </th>
                  <th scope="col" className="text-muted fw-bolder">
                    Date Purchased
                  </th>
                  <th scope="col" className="text-muted fw-bolder">
                    Change
                  </th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Portfolios;
