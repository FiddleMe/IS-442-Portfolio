import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import SignUp from './components/SignUp';
import './App.css';
import ForgotPassword from './components/ForgetPassword';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword/>} />
      </Routes>
    </Router>
  );
}

export default App;
