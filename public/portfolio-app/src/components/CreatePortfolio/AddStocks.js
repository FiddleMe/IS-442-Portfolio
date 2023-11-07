import { FaTimes } from 'react-icons/fa';
import { BsPlusLg } from 'react-icons/bs';
import { getAllStocks } from '../../api/Stock/getAllStocks';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import React, { useState, useEffect } from 'react';
import './AddStock.css';
import { parse } from 'date-fns';

function filterLatestStocks(data) {
  const stocksData = data;
  const groupedStocks = {};

  stocksData.forEach((stock) => {
    const stockId = stock.stockId;
    if (!(stockId in groupedStocks) || stock.date > groupedStocks[stockId].date) {
      groupedStocks[stockId] = {
        stockId: stockId,
        name: stock.name,
        price: stock.price,
        date: stock.date,
        parseDate: parse(stock.date, 'yyyy-MM-dd', new Date()),
      };
    }
  });

  return Object.values(groupedStocks);
}

function transformData(data) {
  return data.map((stock) => ({
    stockId: stock.stockId,
    name: stock.name,
    price: stock.price,
    date: stock.date,
    parseDate: parse(stock.date, 'yyyy-MM-dd', new Date()),
  }));
}

function fillMissingStockData(data) {
  // Sort the dataset by 'date' and then by 'stockId'
  data.sort((a, b) => {
    const dateComparison = new Date(a.date) - new Date(b.date);
    if (dateComparison === 0) {
      return a.stockId.localeCompare(b.stockId);
    }
    return dateComparison;
  });

  const filledData = [];

  const stockDataMap = new Map();

  for (const item of data) {
    if (!stockDataMap.has(item.date)) {
      stockDataMap.set(item.date, new Map());
    }

    stockDataMap.get(item.date).set(item.stockId, item);
  }

  let previousData = null;

  for (const item of data) {
    if (previousData) {
      const currentDate = new Date(item.date);
      const previousDate = new Date(previousData.date);

      // Check if there are missing dates between the current and previous date
      while (currentDate - previousDate > 24 * 60 * 60 * 1000) {
        previousDate.setDate(previousDate.getDate() + 1);

        const missingDate = previousDate.toISOString().slice(0, 10);

        // Create new items for all stocks that are missing for the date
        if (!stockDataMap.has(missingDate)) {
          stockDataMap.set(missingDate, new Map());
        }

        for (const [stockId, stockData] of stockDataMap.get(previousData.date)) {
          if (!stockDataMap.get(missingDate).has(stockId)) {
            // Create a new item using the previous stock data with the missing date
            const missingData = {
              ...stockData,
              date: missingDate,
              parseDate: new Date(missingDate).toDateString(),
            };
            stockDataMap.get(missingDate).set(stockId, missingData);
          }
        }
      }
    }

    previousData = item;
  }

  // Flatten the map to get the filled data
  for (const stockMap of stockDataMap.values()) {
    filledData.push(...stockMap.values());
  }

  return filledData;
}

function AddStocks({ selectedStocks, setSelectedStocks }) {
  const [minDate, setMinDate] = useState(null);
  const [maxDate, setMaxDate] = useState(null);
  const [totalStock, setTotalStocks] = useState(null);
  const [stocks, setStocks] = useState([]);
  const [selectedDates, setSelectedDates] = useState([]);
  const [isMobile, setIsMobile] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  useEffect(() => {
    // Call the getAllStocks function to fetch the list of stocks
    async function fetchStocks() {
      try {
        const data = await getAllStocks();
        const filledStocks = fillMissingStockData(data);
        const transformStocks = transformData(filledStocks);

        setTotalStocks(transformStocks);
        const availableDatesSet = new Set(
          transformStocks.map((stock) => stock.parseDate.toISOString())
        );
        const availableDates = [...availableDatesSet]
          .map((dateStr) => new Date(dateStr))
          .sort((a, b) => a - b);
        const minDate = availableDates[0];
        const maxDate = availableDates[availableDates.length - 1];
        setMinDate(minDate);
        setMaxDate(maxDate);

        const filteredStocks = filterLatestStocks(data);
        console.log(filteredStocks);
        setStocks(filteredStocks);
        const initialSelectedDates = filteredStocks.map((stock) => stock.parseDate);
        setSelectedDates(initialSelectedDates);
      } catch (error) {
        console.error('Error fetching stocks:', error);
      }
    }
    fetchStocks();
  }, []);

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

  const handleDateChange = (date, stockName, index) => {
    const updatedDates = [...selectedDates];
    updatedDates[index] = date;
    setSelectedDates(updatedDates);

    const updatedStocks = [...stocks];
    const selectedDate = new Date(date);

    // Find the stock index based on the stockName
    const stockIndex = updatedStocks.findIndex((stock) => stock.name === stockName);

    // Filter stocks by the selected date and stockName, and update the stocks state
    const stocksByDate = totalStock.filter(
      (stock) => stock.parseDate.getTime() === selectedDate.getTime() && stock.name === stockName
    );

    updatedStocks[stockIndex] = stocksByDate[0];
    setStocks(updatedStocks);
  };

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
            <div
              className="overflow-auto table-responsive"
              style={{ maxHeight: 'calc(100vh - 300px)' }}
            >
              <table className={`table ${isMobile ? 'mobile' : 'website'}`}>
                <thead>
                  <tr>
                    <th className={`small text-secondary col-3 ${isMobile ? 'mobile' : 'website'}`}>
                      Stock Name
                    </th>
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

                      <td className="text-center">
                        <DatePicker
                          selected={selectedDates[index]}
                          onChange={(date) => handleDateChange(date, stock.name, index)}
                          placeholderText="Select Date"
                          dateFormat="yyyy/MM/dd"
                          className="form-control text-sm"
                          // Enable or disable the datepicker based on availability
                          minDate={minDate}
                          maxDate={maxDate}
                        />
                      </td>

                      <td>
                        <button
                          className={`float-end btn btn-outline-primary btn-small buttonFont ${
                            isMobile ? 'mobile_btn' : 'web_btn'
                          }`}
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
                    <th className="small text-secondary col-2">Stock Name</th>
                    <th className="small text-secondary col-2">Unit Price</th>
                    <th className="small text-secondary col-3">Date</th>
                    <th className="small text-secondary col-2">Quantity</th>
                    <th className="small text-secondary col-2">Total Price</th>
                    <th style={{ width: '10%' }}></th>
                  </tr>
                </thead>
                <tbody>
                  {selectedStocks.length > 0 ? (
                    selectedStocks.map((stock, index) => (
                      <tr key={index}>
                        <td>{stock.name}</td>
                        <td>${stock.price.toLocaleString('en-US')}</td>
                        <td>{stock.date}</td>
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
                    ))
                  ) : (
                    <tr>
                      <td colSpan="6">No selected stocks</td>
                    </tr>
                  )}
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
