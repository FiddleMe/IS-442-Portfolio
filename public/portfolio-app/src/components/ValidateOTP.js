import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../App.css";
import axios from "axios";

function ValidateOTP() {
  const location = useLocation();
  const navigate = useNavigate();
  const email = new URLSearchParams(location.search).get("email");
  const [otp, setOtp] = useState(""); // Define and initialize 'otp'

  useEffect(() => {
    console.log("ValidateOTP component is rendering or re-rendering.")
  });

  const handleValidateOTP = async (e) => {
    // Make an Axios POST request to validate OTP
    e.preventDefault();
    axios
      .post(
        `http://localhost:8082/api/users/validateOTP?email=${email}&otp=${otp}`,
        {
          email: email,
          otp: otp,
        }
      )
      .then((response) => {
        console.log(response.data.message);
        console.log("otpValidated set to true");
        alert(response.data.message);
        navigate(`/reset-password?email=${email}`);
      })
      .catch((error) => {
        alert(error);
        console.log(error);
      });
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card rounded-input login-form">
            <div className="card-header">
              <h3 className="header1">Forgot Password</h3>
              <h6 className="header2">Enter OTP sent to your email</h6>
            </div>
            <div className="card-body">
              <form>
                <div className="form-group">
                  <label htmlFor="otp">Enter OTP</label>
                  <input
                    type="text"
                    className="form-control rounded-input"
                    id="otp"
                    placeholder="Enter OTP"
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block rounded-input"
                  onClick={handleValidateOTP}
                >
                  Validate OTP
                </button>
              </form>
            </div>
            <div className="card-footer text-center">
              <small className="text-muted">
                Remember your password?{" "}
                <Link to="/login" className="text-primary">
                  Login
                </Link>
              </small>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ValidateOTP;
