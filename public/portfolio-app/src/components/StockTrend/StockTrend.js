import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';

function StockTrend() {
    const userDetails =
        sessionStorage.getItem('userData') !== null
            ? JSON.parse(sessionStorage.getItem('userData'))
            : null;
    const name = userDetails !== null ? userDetails.firstName + ' ' + userDetails.lastName : '';
    const email = userDetails !== null ? userDetails.email : '';
    const userId = userDetails !== null ? userDetails.userId : '';

    const navigate = useNavigate();
    const currentPage = 'Stocks';
    const stockName = 'AAPL Stock Trend';

    const [stockData, setStockData] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleDataFromSidebar = (data) => {
        console.log('Data from child:', data);
        navigate('/home?selectedPortfolio=' + data);
    };

    const fetchHistoricalStockData = async () => {
        setLoading(true);
        try {
            const stockSymbol = 'AAPL';
            const currentDate = new Date();
            const apiUrl = `http://localhost:8082/api/portfoliostocks/getRangeStockPriceChange/${stockSymbol}/`;
    
            const stockTrend = [];
            
            for (let year = 0; year < 2; year++) {
                const startDate = new Date(currentDate);
                startDate.setFullYear(currentDate.getFullYear() - year);
                const endDate = new Date(currentDate);
                
                const startYear = startDate.getFullYear();
                const endYear = endDate.getFullYear();
                
                const startMonth = (startDate.getMonth() + 1).toString().padStart(2, '0'); // Add leading zero
                const endMonth = (endDate.getMonth() + 1).toString().padStart(2, '0');
                
                const startDay = (startDate.getDate() - 2).toString().padStart(2, '0'); // Add leading zero
                const endDay = endDate.getDate().toString().padStart(2, '0');
                
                // Format the date as 'YYYY-MM-DD' for the API request
                const formattedStartDate = `${startYear}-${startMonth}-${startDay}`;
                const formattedEndDate = `${endYear}-${endMonth}-${endDay}`;                
    
                const yearApiUrl = `${apiUrl}${formattedStartDate}/${formattedEndDate}`;
                const response = await fetch(yearApiUrl);
                const data = await response.json();
    
                if (data.status === 200) {
                    const percentageDifference = data.data.percentageDifference;
                    stockTrend.push({
                        year: startYear,
                        percentageDifference: percentageDifference.toFixed(2),
                    });
                }
            }
    
            console.log(stockTrend);
        } catch (error) {
            console.error('Error fetching stock data:', error);
        }
    };
    

    useEffect(() => {
        fetchHistoricalStockData();
    }, []);

    return (
        <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
            <div className="row">
                <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar} />
                <div className="col-md p-0">
                    <Header name={name} email={email} />

                    <div className="px-5">
                        <h2>{stockName}</h2>
                        <br />
                        <br />

                        {loading ? (
                            <p>Loading...</p>
                        ) : stockData.length > 0 ? (
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th>Year</th>
                                        <th>Percentage Difference</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {stockData.map((item, index) => (
                                        <tr key={index}>
                                            <td>{item.year} years ago</td>
                                            <td>{item.percentageDifference}%</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        ) : (
                            <p>No data available for stock trend.</p>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default StockTrend;
