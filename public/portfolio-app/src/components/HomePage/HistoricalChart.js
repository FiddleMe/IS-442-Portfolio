import React, { useState, useEffect } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ReferenceArea,
} from 'recharts';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import 'bootstrap/dist/css/bootstrap.min.css'; // Make sure you import Bootstrap CSS

function HistoricalChart({ title, historicalData, fetchHistoricalData, SetCurrentHistData }) {
  const [activeButton, setActiveButton] = useState('daily');
  const latestValue = historicalData[historicalData.length - 1].value;

  // useEffect(() => {
  //   fetchHistoricalData('daily');
  // }, []); // Dependency on fetchHistoricalData to re-run if this function changes
  // useEffect(() => {
  //   fetchHistoricalData(activeButton);
  // }, [fetchHistoricalData, activeButton]);

  const handleClick = (interval) => {
    setActiveButton(interval); // Set the active button
    fetchHistoricalData(interval);
  };

  // Custom styling for the buttons
  const buttonStyle = (buttonKey) => ({
    fontWeight: activeButton === buttonKey ? 'bold' : 'normal',
    backgroundColor: 'transparent',
    borderColor: 'transparent',
    color: 'gray',
    boxShadow: 'none',
  });
  return (
    <div className="bg-white rounded-3 text-center">
      <LineChart
        width={600}
        height={400}
        data={historicalData}
        margin={{ top: 0, right: 20, left: 0, bottom: 0 }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" type="category" allowDuplicatedCategory={false} tick={false} />
        <YAxis domain={['dataMin', 'dataMax']} tick={false} padding={{ top: 100, bottom: 100 }} />
        <Tooltip />
        <Line type="monotone" dataKey="value" stroke="#8884d8" />
        <ReferenceArea
          x1={historicalData.length - 1}
          x2={historicalData.length}
          fill="rgba(255, 255, 255, 0.7)"
        />
        <text x={100} y={60} textAnchor="start" fontSize={20} fontWeight="bold" fill="#505050">
          Your Balance{' '}
          <tspan fontSize={30} x={100} y={40}>
            ${latestValue}
          </tspan>
        </text>
      </LineChart>
      <ButtonGroup aria-label="Time period">
        <Button
          style={buttonStyle('daily')}
          variant="outline-secondary"
          onClick={() => handleClick('daily')}
          active={activeButton === 'daily'}
        >
          Daily
        </Button>
        <Button
          style={buttonStyle('weekly')}
          variant="outline-secondary"
          onClick={() => handleClick('weekly')}
          active={activeButton === 'weekly'}
        >
          Weekly
        </Button>
        <Button
          style={buttonStyle('monthly')}
          variant="outline-secondary"
          onClick={() => handleClick('monthly')}
          active={activeButton === 'monthly'}
        >
          Monthly
        </Button>
      </ButtonGroup>
      <br />
      <span className="fw-bold">{title}</span>
    </div>
  );
}

export default HistoricalChart;
