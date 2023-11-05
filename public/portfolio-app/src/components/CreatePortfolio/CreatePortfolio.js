import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import AddStocks from './AddStocks';
import { useNavigate } from 'react-router-dom';


function CreatePortfolio() {

  const userDetails = JSON.parse(sessionStorage.getItem('userData'));
  const name = userDetails.firstName + ' ' + userDetails.lastName;
  const email = userDetails.email;
  const navigate = useNavigate();

  const currentPage = 'Create Portfolio';

  const [portfolioName, setPortfolioName] = useState('New Portfolio');
  const [description, setDescription] = useState('New Description');
  const [capitalAmount, setCapitalAmount] = useState(0);

  const handleNameChange = (e) => {
    setPortfolioName(e.target.value || 'New Portfolio');
  };
  const handleDescriptionChange = (e) => {
    setDescription(e.target.value || 'New Description');
  };

  const handleCapitalAmountChange = (e) => {
    setCapitalAmount(e.target.value || 'New Capital');
  };

  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    navigate('/home?selectedPortfolio=' + data);
  };

  const handleCreatePortfolio = async () => {
    const data = {
      name: portfolioName,
      description: description, // Include description from state
      capitalAmount: capitalAmount, // Include capitalAmount from state
      userId: userDetails.userId,
    };
    try {
      const response = await fetch('http://localhost:8082/api/portfolio', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        const responseData = await response.json(); // Parse the response body as JSON
        console.log(responseData.data);

        alert('Portfolio created successfully');
      } else {
        console.log('Failed to create portfolio');
      }
    } catch (error) {
      console.error('An error occurred while creating a portfolio:', error);
    }
  };

  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
      <Sidebar userId={userDetails.userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar}/>
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
                    value={capitalAmount} // Set the value from state
                    onChange={handleCapitalAmountChange} // Handle change using the change handler
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
                value={description === 'New Description' ? '' : description} // Set the value from state
                onChange={handleDescriptionChange} // Handle change using the change handler
              ></textarea>
            </div>
            <br />

            <AddStocks />
            <button className="btn btn-primary" onClick={handleCreatePortfolio}>
              Create Portfolio
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreatePortfolio;
