import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Sidebar from '../Sidebar';
import Header from '../Header';
import { FaBookOpen } from 'react-icons/fa';
import DonutChart from './DonutChart';
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
            <DonutChart title={'industry'} data={data} />
            <DonutChart title={'Geographical Distrubution'} data={GeographicalData} />
          </div>
          <div className="m-2 d-flex flex-wrap gap-4">
            <Portfolios PortfolioData = {PortfolioData}/>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
