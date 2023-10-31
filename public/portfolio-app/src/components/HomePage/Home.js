import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { FaBookOpen } from 'react-icons/fa';
import DonutChart from './DonutChart';

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
import Portfolios from './Portfolios';

let data = [
  { value: 10, label: 'Technology' },
  { value: 4, label: 'Finance' },
  { value: 26, label: 'Construction' },
];

let GeographicalData = [
  { value: 10, label: 'North America' },
  { value: 8, label: 'Europe' },
  { value: 5, label: 'Asia' },
  { value: 4, label: 'South America' },
  { value: 2, label: 'Africa' },
  { value: 1, label: 'Australia' },
];
let historicalData = [
  { name: '2023-01-01', value: 100.0 },
  { name: '2023-01-02', value: 150.0 },
  { name: '2023-01-03', value: 120.0 },
  { name: '2023-01-04', value: 200.0 },
  // Add more data points with date and value
];
const latestValue = historicalData[historicalData.length - 1].value;

let PortfolioData = [
  {
    name: 'Portfolio A',
    capitalAmount: 100000,
    change: 5000,
  },
  {
    name: 'Portfolio B',
    capitalAmount: 75000,
    change: -2000,
  },
  {
    name: 'Portfolio C',
    capitalAmount: 120000,
    change: 8000,
  },
  {
    name: 'Portfolio D',
    capitalAmount: 90000,
    change: -5000,
  },
];

function Home() {
  const subPages = [
    { icon: FaBookOpen, title: 'Portfolio 1' },
    { icon: FaBookOpen, title: 'Portfolio 2' },
  ];
  const userDetails = JSON.parse(sessionStorage.getItem('userData'));
  const name = userDetails.firstName + ' ' + userDetails.lastName;
  const email = userDetails.email;

  const currentPage = 'Portfolio 1';

  return (
    <div className="container-fluid" style={{ backgroundColor: '#F8F9FD' }}>
      <div className="row">
        <Sidebar subPages={subPages} currentPage={currentPage} />
        <div className="col-md p-0">
          <Header name={name} email={email} />
          <div className="m-2 d-flex flex-wrap gap-4">
            <div className="bg-white">
              <LineChart width={600} height={400} data={historicalData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis
                  dataKey="name"
                  type="category"
                  allowDuplicatedCategory={false}
                  tick={false}
                />
                <YAxis tick={false} />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="value" stroke="#8884d8" />
                <ReferenceArea
                  x1={historicalData.length - 1}
                  x2={historicalData.length}
                  fill="rgba(255, 255, 255, 0.7)"
                />
                <text
                  x={100}
                  y={50}
                  textAnchor="start"
                  fontSize={20}
                  fontWeight="bold"
                  fill="#505050"
                >
                  Your Balance{' '}
                  <tspan fontSize={30} x={100} dy={30}>
                    ${latestValue}
                  </tspan>
                </text>
              </LineChart>
            </div>
            <DonutChart title={'industry'} data={data} />
            <DonutChart title={'Geographical Distrubution'} data={GeographicalData} />
          </div>
          <div className="">
            <Portfolios PortfolioData={PortfolioData} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
