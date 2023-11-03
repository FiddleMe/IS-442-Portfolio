import { useEffect, useState } from 'react';
import { FaTimes } from 'react-icons/fa';
import { BsPlusLg } from 'react-icons/bs';
import { getAllStocks } from '../../api/Stock/getAllStocks';
// import filterLatestStocks from '../../utils/filterLatestDateStock';

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

  const [stocks, setStocks] = useState([]);

  const [selectedStocks, setSelectedStocks] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');

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
            <div className="overflow-auto" style={{ maxHeight: 'calc(100vh - 300px)' }}>
              <table className="table">
                <thead>
                  <tr>
                    <th className="small text-secondary" style={{ width: '50%' }}>
                      Stock Name
                    </th>
                    <th className="small text-secondary" style={{ width: '25%' }}>
                      Price
                    </th>
                    <th style={{ width: '25%' }}></th>
                  </tr>
                </thead>
                <tbody>
                  {filteredStocks.map((stock, index) => (
                    <tr key={index}>
                      <td>{stock.name}</td>
                      <td>${stock.price.toLocaleString('en-US')}</td>
                      <td>
                        <button
                          className="float-end btn btn-outline-primary buttonFont"
                          onClick={() => handleAddStock(stock)}
                        >
                          <BsPlusLg className="pb-1 buttonIcon" /> Add Stocks
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
