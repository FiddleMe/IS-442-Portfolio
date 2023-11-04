import { FaTimes } from 'react-icons/fa';
import { BsPlusLg } from 'react-icons/bs';
import { getAllStocks } from '../../api/Stock/getAllStocks';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css'; 
import React, { useState, useEffect } from 'react';
import './AddStock.css';


function filterLatestStocks(data) {
  const stocksData = data;
  const groupedStocks = {};

  stocksData.forEach((stock) => {
    const stockId = stock.stockId;
    if (!(stockId in groupedStocks) || stock.date > groupedStocks[stockId].date) {
      groupedStocks[stockId] = {
        name: stock.name,
        price: stock.price,
      };
    }
  });

  return Object.values(groupedStocks);
}

function AddStocks() {
  const [stocks, setStocks] = useState([]); // Move the useState here
  const [selectedDate, setSelectedDate] = useState(null);
  useEffect(() => {
    // Call the getAllStocks function to fetch the list of stocks
    async function fetchStocks() {
      try {
        const data = await getAllStocks();
        // Set the retrieved stocks to the state

        const filteredStocks = filterLatestStocks(data);
        console.log(filteredStocks);

        setStocks(filteredStocks);
      } catch (error) {
        console.error('Error fetching stocks:', error);
      }
    }

    // Call the fetchStocks function
    fetchStocks();
  }, []);


  const [selectedStocks, setSelectedStocks] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [isMobile, setIsMobile] = useState(false);

  const handleAddStock = (stock) => {
    const existingStock = selectedStocks.find((s) => s.name === stock.name);
    if (existingStock) {
      const newSelectedStocks = [...selectedStocks];
      const existingStockIndex = newSelectedStocks.indexOf(existingStock);
      newSelectedStocks[existingStockIndex].quantity += 1;
      setSelectedStocks(newSelectedStocks);
    } else {
      setSelectedStocks([...selectedStocks, { ...stock, quantity: 1 }]);
    }
  };

  const handleQuantityChange = (index, quantity) => {
    const newSelectedStocks = [...selectedStocks];
    newSelectedStocks[index].quantity = quantity;
    setSelectedStocks(newSelectedStocks);
  };

  const filteredStocks = stocks.filter((stock) =>
    stock.name.toLowerCase().includes(searchQuery.toLowerCase())
  );
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth <= 768) {
        setIsMobile(true);
      } else {
        setIsMobile(false);
      }
    };

    window.addEventListener('resize', handleResize);

    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <div className="">
      <div className="row">
        <div className="col-md-6">
          <div className="card p-3 h-100">
            <h2>Add Stocks</h2>
            <input
              type="text"
              placeholder="Search Stock"
              className="rounded-3 px-3 py-2 w-100 mb-3"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            ></input>
            <div className="overflow-auto table-responsive" style={{ maxHeight: 'calc(100vh - 300px)' }}>
              <table className={`table ${isMobile ? 'mobile' : 'website'}`}>
                <thead>
                  <tr>
                    <th className={`small text-secondary col-3 ${isMobile ? 'mobile' : 'website'}`}>Stock Name</th>
                    <th className="small text-secondary col-2">Price</th>
                    <th className="small text-secondary col-3">Date</th>
                    <th className="col-2"></th>
                  </tr>
                </thead>

                <tbody>
                  {filteredStocks.map((stock, index) => (
                    <tr key={index}>
                      <td className={`${isMobile ? 'mobile' : 'website'}`}>{stock.name}</td>
                      <td>${stock.price.toLocaleString('en-US')}</td>
                      <td className="text-center" >
                        <div className="datePickerWrapper">
                          <DatePicker
                            selected={selectedDate}
                            onChange={(date) => setSelectedDate(date)}
                            placeholderText="Select Date"
                            dateFormat="yyyy/MM/dd"
                            className="form-control text-sm datepicker"
                          />
                        </div>
                      </td>
                      <td>
                        <button
                          className={`float-end btn btn-outline-primary btn-small buttonFont ${isMobile ? 'mobile_btn' : 'web_btn'}`}
                          onClick={() => handleAddStock(stock)}
                          
                        >
                          <BsPlusLg className="pb-1 buttonIcon " /> 
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div className="col-md-6">
          <div className="card p-3 h-100">
            <h2>Cart</h2>
            <div className="overflow-auto" style={{ maxHeight: 'calc(100vh - 300px)' }}>
              <table className="table">
                <thead>
                  <tr>
                    <th className="small text-secondary" style={{ width: '30%' }}>
                      Stock Name
                    </th>
                    <th className="small text-secondary" style={{ width: '20%' }}>
                      Unit Price
                    </th>
                    <th className="small text-secondary" style={{ width: '20%' }}>
                      Quantity
                    </th>
                    <th className="small text-secondary" style={{ width: '20%' }}>
                      Total Price
                    </th>
                    <th style={{ width: '10%' }}></th>
                  </tr>
                </thead>
                <tbody>
                  {selectedStocks.map((stock, index) => (
                    <tr key={index}>
                      <td>{stock.name}</td>
                      <td>${stock.price.toLocaleString('en-US')}</td>
                      <td>
                        <input
                          type="number"
                          min="1"
                          value={stock.quantity}
                          onChange={(e) => handleQuantityChange(index, parseInt(e.target.value))}
                          className="form-control form-control-sm rounded"
                        />
                      </td>
                      <td>
                        $
                        {(stock.price * stock.quantity).toLocaleString('en-US', {
                          minimumFractionDigits: 2,
                          maximumFractionDigits: 2,
                        })}
                      </td>
                      <td>
                        <FaTimes
                          className="text-danger"
                          style={{ cursor: 'pointer' }}
                          onClick={() =>
                            setSelectedStocks(selectedStocks.filter((s) => s !== stock))
                          }
                        />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddStocks;
