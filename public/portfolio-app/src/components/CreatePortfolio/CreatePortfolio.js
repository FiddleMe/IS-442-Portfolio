import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import AddStocks from './AddStocks';
import { FaBookOpen } from 'react-icons/fa';

function CreatePortfolio() {
  const subPages = [
    { icon: FaBookOpen, title: 'Portfolio 1' },
    { icon: FaBookOpen, title: 'Portfolio 2' },
  ];
  const userDetails = JSON.parse(sessionStorage.getItem('userData'));
  const name = userDetails.firstName + ' ' + userDetails.lastName;
  const email = userDetails.email;

  const currentPage = 'Create Portfolio';

  const [portfolioName, setPortfolioName] = useState('New Portfolio');

  const handleNameChange = (e) => {
    setPortfolioName(e.target.value || 'New Portfolio');
  };

  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar subPages={subPages} currentPage={currentPage} />
        <div className="col-md p-0">
          <Header name={name} email={email} />
          <div className="px-5">
            <h2>{portfolioName}</h2>
            <br />

            <div className="row">
              <div className="col-md-6">
                <div className="form-group">
                  <label htmlFor="portfolioName">Name:</label>
                  <input
                    type="text"
                    className="form-control rounded-3"
                    id="portfolioName"
                    placeholder="Enter portfolio name"
                    value={portfolioName === 'New Portfolio' ? '' : portfolioName}
                    onChange={handleNameChange}
                  />
                </div>
              </div>
              <div className="col-md-6">
                <div className="form-group">
                  <label htmlFor="capitalAmount">Capital Amount:</label>
                  <input
                    type="number"
                    className="form-control rounded-3"
                    id="capitalAmount"
                    placeholder="Enter capital amount ($)"
                  />
                </div>
              </div>
            </div>
            <br />

            <div className="form-group">
              <label htmlFor="description">Description:</label>
              <textarea
                className="form-control rounded-3"
                id="description"
                rows="3"
                placeholder="Enter description"
              ></textarea>
            </div>
            <br />

            <AddStocks />

          </div>
        </div>
      </div>
    </div>
  );
}

export default CreatePortfolio;