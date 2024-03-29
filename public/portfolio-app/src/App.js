import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import SignUp from './components/SignUp';
import ForgotPassword from './components/ForgetPassword';
import ResetPassword from './components/ResetPassword';
import ValidateOTP from './components/ValidateOTP';
import Home from './components/HomePage/Home';
import CreatePortfolio from './components/CreatePortfolio/CreatePortfolio';
import Stocks from './components/StocksPage/Stocks';
import StockTrend from './components/StockTrend/StockTrend';
import PriceDifferenceChart from './components/StockTrend/StockChart';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/validate-otp" element={<ValidateOTP />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/home" element={<Home />} />
        <Route path="/create-portfolio" element={<CreatePortfolio />} />
        <Route path="/stock-trend" element={<StockTrend />} />
        <Route path="/stocks" element={<Stocks />} />
        <Route path="/stockchart" element={<PriceDifferenceChart />} />
      </Routes>
    </Router>
  );
}

export default App;
