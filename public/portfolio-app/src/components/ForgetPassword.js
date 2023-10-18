import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../App.css";
import axios from 'axios';

function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [otpSent, setOtpSent] = useState(false);

  const handleSendOtp = (e) => {
    e.preventDefault();
    // Send OTP to the provided email (you can add your logic here).
    // After OTP is sent, set otpSent to true.
    setOtpSent(true);
  }

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card rounded-input login-form">
            <div className="card-header">
              <h3 className="header1">Forgot Password</h3>
              <h6 className="header2">Enter your email to reset your password</h6>
            </div>
            <div className="card-body">
              {otpSent ? (
                <form>
                  <div className="form-group">
                    <label htmlFor="otp">Enter OTP</label>
                    <input type="text" className="form-control rounded-input" id="otp" placeholder="Enter OTP" />
                  </div>
                  <div className="form-group">
                    <label htmlFor="newPassword">New Password</label>
                    <input type="password" className="form-control rounded-input" id="newPassword" placeholder="Enter new password" />
                  </div>
                  <div className="form-group">
                    <label htmlFor="confirmPassword">Confirm Password</label>
                    <input type="password" className="form-control rounded-input" id="confirmPassword" placeholder="Confirm new password" />
                  </div>
                  <button type="submit" className="btn btn-primary btn-block rounded-input">Reset Password</button>
                </form>
              ) : (
                <form>
                  <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                      type="email"
                      className="form-control rounded-input"
                      id="email"
                      placeholder="Enter your email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </div>
                  <button type="submit" className="btn btn-primary btn-block rounded-input" onClick={handleSendOtp}>
                    Send OTP
                  </button>
                </form>
              )}
            </div>
            <div className="card-footer text-center">
              <small className="text-muted">
                Remember your password? <Link to="/login" className="text-primary">Login</Link>
              </small>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;
