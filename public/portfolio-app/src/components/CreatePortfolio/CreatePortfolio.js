import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import AddStocks from './AddStocks';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { createPortfolio } from '../../api/Portfolio/createPortfolioApi';
import { transformPortfolioStocks } from '../../utils/transformPortfolioStocks';
import { createPortfolioStocks } from '../../api/PortfolioStocks/createPortfolioStocks';
import { updatePortfolio } from '../../api/Portfolio/updatePortfolio';
import { postSegment } from '../../api/Segment/postSegment';
import { postEmail } from '../../api/Email/postEmail';
import DonutChart from '../HomePage/DonutChart';

function CreatePortfolio() {
  const userDetails =
    sessionStorage.getItem('userData') != null
      ? JSON.parse(sessionStorage.getItem('userData'))
      : null;
  const name = userDetails != null ? userDetails.firstName + ' ' + userDetails.lastName : '';
  const email = userDetails != null ? userDetails.email : '';
  const userId = userDetails != null ? userDetails.userId : '';

  const navigate = useNavigate();

  const currentPage = 'Create Portfolio';

  const [portfolioName, setPortfolioName] = useState('New Portfolio');
  const [description, setDescription] = useState('New Description');
  const [capitalAmount, setCapitalAmount] = useState(0);
  const [selectedStocks, setSelectedStocks] = useState([]);
  const [sectorData, setSectorData] = useState([]);

  useEffect(() => {
    const checkSessionStorage = () => {
      if (sessionStorage.getItem('userData') === null) {
        navigate('/');
        return;
      }
    };

    checkSessionStorage();
  }, []);
  const handleNameChange = (e) => {
    setPortfolioName(e.target.value || 'New Portfolio');
  };
  const handleDescriptionChange = (e) => {
    setDescription(e.target.value || 'New Description');
  };

  const handleCapitalAmountChange = (e) => {
    setCapitalAmount(e.target.value);
  };

  const handleDataFromSidebar = (data) => {
    console.log('Data from child:', data);
    navigate('/home?selectedPortfolio=' + data);
  };

  const handleCreatePortfolio = async () => {
    try {
      let totalPrice = 0;
      for (let i = 0; i < selectedStocks.length; i++) {
        const qty = selectedStocks[i].quantity;
        const price = selectedStocks[i].price;
        totalPrice += price * qty;
        console.log('PRICE', totalPrice);
      }

      const totalBalance = capitalAmount - totalPrice;

      if (totalBalance < 0) {
        Swal.fire({
          icon: 'error',
          title: 'Insufficient Balance to add stocks!',
          text: 'Please adjust your capital amount',
          footer: '',
        });
      } else {
        const data = {
          name: String(portfolioName),
          description: String(description),
          capitalAmount: parseFloat(capitalAmount),
          userId: String(userDetails.userId),
          balance: totalBalance,
        };
        const response = await createPortfolio(data);
        var postedSegment = null;

        if (response) {
          Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Portfolio created successfully',
            footer: '',
            showConfirmButton: true,
          }).then(async (result) => {
            if (result.isConfirmed) {
              try {
                postedSegment = postSegment(response.portfolioId, selectedStocks);
              } catch (error) {
                console.log(error);
              }
              if (postedSegment) {
                const transformed = transformPortfolioStocks(selectedStocks, response.portfolioId);
                const responseArr = [];
                for (let i = 0; i < transformed.length; i++) {
                  const stockResponse = await createPortfolioStocks(transformed[i]);
                  if (stockResponse) {
                    responseArr.push(stockResponse);
                  }
                }

                if (responseArr.length === transformed.length) {
                  const updateData = { balance: totalBalance };
                  const updatedPortfolio = await updatePortfolio(response.portfolioId, updateData);

                  if (updatedPortfolio) {
                    const emailBody = {
                      recipient: email,
                      subject: 'Created Portfolio successfully!',
                      msg: `Dear ${name}, your portfolio of portfolio ID ${response.portfolioId} has been created!`,
                    };
                    postEmail(emailBody);
                    Swal.fire({
                      icon: 'success',
                      title: 'Success!',
                      text: 'Stocks added successfully',
                      footer: '',
                      showConfirmButton: true,
                    }).then((result) => {
                      if (result.isConfirmed) {
                        navigate('/home');
                      }
                    });
                  } else {
                    Swal.fire({
                      icon: 'error',
                      title: 'Oops...',
                      text: 'Failed to add stocks',
                      footer: 'Try Again!',
                      showConfirmButton: true,
                    });

                    navigate('/home');
                  }
                }
              }
            }
          });
        } else {
          console.log('Failed to create portfolio');
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Failed to create portfolio',
            footer: 'Try Again!',
          });
        }
      }
    } catch (error) {
      console.error('An error occurred while creating a portfolio:', error);
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: error,
        footer: 'Try Again!',
      });
    }
  };


  function handleSectorValuesChange(sectorValues) {
    //from add stocks
    setSectorData(sectorValues);
  }
  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar} />
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
            {sectorData.length > 0 ? (
              <DonutChart title={'Industry Distribution'} data={sectorData} />
            ) : (
              <p className="fs-5 fw-normal text-dark text-center">Add Stocks to Get Industry Distribution</p> // Replace this with what you want to display when there's no data
            )}
            <br />

            <AddStocks
              selectedStocks={selectedStocks}
              setSelectedStocks={setSelectedStocks}
              onSectorValuesChange={handleSectorValuesChange}
            />
            <button className="btn btn-primary my-3" onClick={handleCreatePortfolio}>
              Create Portfolio
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreatePortfolio;
