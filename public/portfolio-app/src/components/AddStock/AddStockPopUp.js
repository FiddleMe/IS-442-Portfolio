import { Modal } from '@mui/joy';
import React, { useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import AddStocks from '../CreatePortfolio/AddStocks'; // Assuming this is the path to your AddStocks component
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { createPortfolioStocks } from '../../api/PortfolioStocks/createPortfolioStocks';
import Swal from 'sweetalert2';
import { updatePortfolio } from '../../api/Portfolio/updatePortfolio';
import { transformPortfolioStocks } from '../../utils/transformPortfolioStocks';

const modalStyle = {
  width: '80%',
};

function AddStockPopUp({ portfolio }) {
  const [isModalOpen, setModalOpen] = useState(false);
  const [selectedStocks, setSelectedStocks] = useState([]);

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };

  const handlePostStocksInParent = async () => {
    const walletBalance = portfolio.wallet;
    let totalPrice = 0;
    for (var i = 0; i < selectedStocks.length; i++) {
      const qty = selectedStocks[i].quantity;
      const price = selectedStocks[i].price;
      totalPrice += price * qty;
    }
    const totalBalance = walletBalance - totalPrice;
    if (totalBalance < 0) {
      closeModal();
      Swal.fire({
        icon: 'error',
        title: 'Insufficient Balance!',
        text: 'You have insufficient balance',
        footer: '',
      });
    } else {
      const transformed = transformPortfolioStocks(selectedStocks, portfolio.portfolioId);
      var responseArr = [];
      try {
        for (var i = 0; i < transformed.length; i++) {
          const response = await createPortfolioStocks(transformed[i]);
          if (response) {
            responseArr.push(response);
          }
        }

        if (responseArr.length === transformed.length) {
          const data = { wallet: totalBalance };
          const updatedPortfolio = await updatePortfolio(portfolio.portfolioId, data);
          if (updatedPortfolio) {
            closeModal();
            Swal.fire({
              icon: 'success',
              title: 'Success!',
              text: 'Stocks added successfully',
              footer: '',
              showConfirmButton: true,
            }).then((result) => {
              if (result.isConfirmed) {
                // Refresh the window
                window.location.reload();
              }
            });
          }
        } else {
          closeModal();
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Failed to add stocks',
            footer: 'Try Again!',
            showConfirmButton: true,
          });
        }
      } catch (error) {
        closeModal();
        console.error('An error occurred while adding a stock:', error);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error,
          footer: 'Try Again!',
        });
      }
    }
  };
  return (
    <div>
      <span className="fw-bolder">Stocks</span>

      <button
        onClick={openModal}
        className="float-end  pb-1 mt-n2 btn btn-outline-primary buttonFont"
      >
        <BsPlusLg className="pb-2buttonIcon" /> Add Stocks
      </button>

      <Modal open={isModalOpen} onClose={closeModal}>
        <div
          className="position-relative w-80 top-50 start-50 translate-middle bg-white p-4 rounded-2"
          style={modalStyle}
        >
          <FontAwesomeIcon
            icon={faTimes}
            className="position-absolute top-0 end-0 mt-1 me-4 fs-5"
            style={{ cursor: 'pointer' }}
            onClick={closeModal}
          />
          <AddStocks selectedStocks={selectedStocks} setSelectedStocks={setSelectedStocks} />
          <button
            className="btn btn-primary mt-3 position-relative "
            onClick={handlePostStocksInParent}
          >
            Add Stock
          </button>
        </div>
      </Modal>
    </div>
  );
}

export default AddStockPopUp;
