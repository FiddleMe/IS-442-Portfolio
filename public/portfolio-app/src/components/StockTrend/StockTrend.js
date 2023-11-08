import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import DateSlider from './DateSlider'
import StockDropdown from './StockDropDown'; // Adjust the import path as needed
import Sidebar from '../Sidebar';
import Header from '../Header';
import { useNavigate } from 'react-router-dom';
import StockChart from './StockChart';
import './StockTrend.css'

const positiveColor = {
    color: 'green',
};

const negativeColor = {
    color: 'red',
};

function StockTrend() {
    const userDetails =
        sessionStorage.getItem('userData') !== null
            ? JSON.parse(sessionStorage.getItem('userData'))
            : null;
    const name = userDetails !== null ? userDetails.firstName + ' ' + userDetails.lastName : '';
    const email = userDetails !== null ? userDetails.email : '';
    const userId = userDetails !== null ? userDetails.userId : '';

    const navigate = useNavigate();
    const currentPage = 'Stock Trend';
    const [selectedStock, setSelectedStock] = useState('AAPL');

    const [stockData, setStockData] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleDataFromSidebar = (data) => {
        console.log('Data from child:', data);
        navigate('/home?selectedPortfolio=' + data);
    };
    
    // Prevent user for entering the page with going through login
    useEffect(() => {
        const checkSessionStorage = () => {
          if (sessionStorage.getItem('userData') === null) {
            navigate('/');
            return;
          }
        };
    
        checkSessionStorage();
      }, []);

    const [portfolioIds, setPortfolioIds] = useState([]);

    const fetchPortfolioData = async () => {
        try {
            const response = await fetch('http://localhost:8082/api/portfolio');
            const data = await response.json();
            if (data.status === 200) {
                const ids = data.data.map((portfolio) => portfolio.portfolioId);
                setPortfolioIds(ids);
            }
        } catch (error) {
            console.error('Error fetching portfolio data:', error);
        }
    };

    useEffect(() => {
        fetchPortfolioData();
    }, []);

    const [stockIds, setStockIds] = useState(new Set());

    const fetchStockIdsFromPortfolios = async () => {
        const allStockIds = new Set(); // Use a Set to store unique stock IDs

        for (const portfolioId of portfolioIds) {
            try {
                const apiUrl = `http://localhost:8082/api/portfoliostocks/${portfolioId}`;
                const response = await fetch(apiUrl);
                const data = await response.json();
                if (data.status === 200) {
                    const ids = data.data.map((stock) => stock.stockId);
                    ids.forEach((stockId) => allStockIds.add(stockId)); // Add to the Set
                }
            } catch (error) {
                console.error(`Error fetching stock data for portfolio ${portfolioId}:`, error);
            }
        }

        // Convert the Set to an array before setting it in state
        setStockIds(Array.from(allStockIds));
    };

    useEffect(() => {
        if (portfolioIds.length > 0) {
            fetchStockIdsFromPortfolios();
        }
    }, [portfolioIds]);

    const [startYear, setStartYear] = useState(new Date().getFullYear() - 20); // Set initial start year
    const [endYear, setEndYear] = useState(new Date().getFullYear()); // Set initial end year

    const fetchHistoricalStockData = async () => {
        setLoading(true);
        try {
            const stockSymbol = selectedStock;
            const apiUrl = `http://localhost:8082/api/portfoliostocks/getRangeStockPriceChange/${stockSymbol}`;

            const stockTrend = [];

            const currentDate = new Date();
            for (let year = startYear; year <= endYear; year++) {
                const startDate = new Date(year, 0, 5); // Start of the selected year
                let endDate = new Date(year, 11, 20); // End of the selected year
            
                if (year === currentDate.getFullYear()) {
                    // If it's the current year, adjust the end date to today - 2 days
                    const twoDaysAgo = new Date(currentDate);
                    twoDaysAgo.setDate(currentDate.getDate() - 2);
                    endDate = twoDaysAgo;
                }

                const startMonth = (startDate.getMonth() + 1).toString().padStart(2, '0');
                const endMonth = (endDate.getMonth() + 1).toString().padStart(2, '0');
                const startDay = startDate.getDate().toString().padStart(2, '0');
                const endDay = endDate.getDate().toString().padStart(2, '0');

                const formattedStartDate = `${year}-${startMonth}-${startDay}`;
                const formattedEndDate = `${year}-${endMonth}-${endDay}`;

                const yearApiUrl = `${apiUrl}/${formattedStartDate}/${formattedEndDate}`;
                const response = await fetch(yearApiUrl);
                const data = await response.json();
                console.log(data)

                if (data.status === 200) {
                    const percentageDifference = data.data.percentageDifference;
                    stockTrend.push({
                        year: year,
                        percentageDifference: percentageDifference.toFixed(2),
                    });
                }
            }
            // Set the stockData state with the fetched data
            setStockData(stockTrend);
            // console.log(stockTrend)
            setLoading(false); // After setting the data, mark loading as false
        } catch (error) {
            console.error('Error fetching stock data:', error);
            setLoading(false); // Make sure to mark loading as false in case of an error
        }
    };

    useEffect(() => {
        fetchHistoricalStockData();
    }, [selectedStock, startYear, endYear]);

    const [averagePercentageChange, setAveragePercentageChange] = useState(null);

    // Calculate the average percentage change from stockData
    useEffect(() => {
        if (stockData.length > 0) {
            const totalPercentageChange = stockData.reduce(
                (accumulator, item) => accumulator + parseFloat(item.percentageDifference),
                0
            );
            const average = totalPercentageChange / stockData.length;
            setAveragePercentageChange(average);
        }
    }, [stockData, startYear, endYear]);

    // Calculate and display how this year's percentage compares to the average
    const thisYearPercentageDifference =
        stockData.length > 0 ? parseFloat(stockData[0].percentageDifference) : null;

    const comparisonToAverage =
        thisYearPercentageDifference !== null && averagePercentageChange !== null
            ? thisYearPercentageDifference - averagePercentageChange
            : null;
    return (
        <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
            <div className="row">
                <Sidebar userId={userId} currentPage={currentPage} onDataToParent={handleDataFromSidebar} />
                <div className="col-md p-0">
                    <Header name={name} email={email} />

                    <div className="px-5">
                        <h2>{selectedStock} Stock Trend</h2>
                        <br />
                        <br />
                        <div className='row'>
                            <div className='col-3'>
                                <StockDropdown stockIds={stockIds} setSelectedStock={setSelectedStock} />
                            </div>
                            <div className='col-8 mx-auto'>
                            <div>
                            <DateSlider
                                minYear={new Date().getFullYear() - 20} // Minimum year (20 years ago)
                                maxYear={new Date().getFullYear()} // Maximum year (current year)
                                value={[startYear, endYear]}
                                onChange={([start, end]) => {
                                    setStartYear(start);
                                    setEndYear(end);
                                }}
                            />
                        </div>
                            </div>
                        </div>
                        {loading ? (
                            <p>Loading...</p>
                        ) : stockData.length > 0 ? (
                            <div>
                                <div className='chart-container text-center mx-auto mb-5'>
                                    <StockChart data={stockData} />
                                </div>
                                <div className='text-center'>
                                    {averagePercentageChange !== null && thisYearPercentageDifference !== null && (
                                        <p>
                                            The average % change from {startYear} to {endYear} is
                                            <span style={{ color: averagePercentageChange >= 0 ? 'green' : 'red' }}>
                                                {averagePercentageChange >= 0 ? '+' : '-'}
                                                {Math.abs(averagePercentageChange).toFixed(2)}% {". "}
                                            </span>
                                            {startYear}'s percentage is{' '}
                                            <span style={{ color: thisYearPercentageDifference >= 0 ? 'green' : 'red' }}>
                                                {thisYearPercentageDifference >= 0 ? '+' : '-'}
                                                {Math.abs(thisYearPercentageDifference).toFixed(2)}%
                                            </span>
                                            , which is {comparisonToAverage >= 0 ? 'higher' : 'lower'}
                                            <span style={{ color: comparisonToAverage >= 0 ? 'green' : 'red' }}>
                                                {" "}{Math.abs(comparisonToAverage).toFixed(2)}%{' '}
                                            </span>
                                            than the average.
                                        </p>

                                    )}
                                </div>
                                <table className="table text-center mt-3">
                                    <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Percentage Difference</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {stockData.map((item, index) => (
                                            <tr key={index}>
                                                <td>{item.year}</td>
                                                <td style={item.percentageDifference >= 0 ? positiveColor : negativeColor} >{item.percentageDifference}%</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>

                            </div>
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
