import { Modal } from '@mui/joy';
import React, { useState } from 'react';
import { BsPlusLg } from 'react-icons/bs';
import AddStocks from '../CreatePortfolio/AddStocks'; // Assuming this is the path to your AddStocks component
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const modalStyle = {
  width: '80%',
};

function AddStockPopUp() {
  const [isModalOpen, setModalOpen] = useState(false);

  const openModal = () => {
    setModalOpen(true);
    console.log('click');
  };

  const closeModal = () => {
    setModalOpen(false);
  };
  function handleAddStock() {
    console.log('add');
  }
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
          <AddStocks />
          <button className="btn btn-primary mt-3 position-relative " onClick={handleAddStock}>
            Add Stock
          </button>
        </div>
      </Modal>
    </div>
  );
}

export default AddStockPopUp;
