import React from 'react';
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

function HistoricalChart({ title, historicalData }) {
  const latestValue = historicalData[historicalData.length - 1].value;
  return (
    <div className="bg-white rounded-3 text-center">
      <LineChart width={600} height={400} data={historicalData}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" type="category" allowDuplicatedCategory={false} tick={false} />
        <YAxis domain={['dataMin', 'dataMax']} tick={false} padding={{ top: 100, bottom: 100 }} />
        <Tooltip />
        <Legend />
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
      <span className="fw-bold">{title}</span>
    </div>
  );
}

export default HistoricalChart;
