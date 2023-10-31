import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../App.css";
import axios from "axios";
import Swal from 'sweetalert2';

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
        Swal.fire({
          icon: 'success',
          title: 'Nice!',
          text: response.data.message,
          footer: ''
        });
        navigate(`/reset-password?email=${email}`);
      })
      .catch((error) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: error,
          footer: 'Try Again!'
        });
      });
  };
  const componentStyles = {
    backgroundColor: "#101729", // Background color
    borderRadius: "25px", // Border radius
    padding: "20px", // Padding
    textAlign: "center", // Text alignment
    position: "relative", // Position
  };


  const header1Styles = {
    color: "#899CF8",
  };

  const header2Styles = {
    color: "#FFFFFF",
  };

  return (
    <div className="container mt-5" style={componentStyles}>
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card rounded-input login-form" style={componentStyles}>
            <div className="card-header">
              <h3 style={header1Styles}>Forgot Password</h3>
              <h6 style={header2Styles}>Enter OTP sent to your email</h6>
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
              <small className="header2">
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
