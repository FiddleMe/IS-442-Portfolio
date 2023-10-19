import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../App.css";
import axios from "axios";

function ForgotPassword() {
    const [email, setEmail] = useState(""); // Define and initialize 'email'
    const [otp, setOtp] = useState(""); // Define and initialize 'otp'
    const [otpSent, setOtpSent] = useState(false);
    const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();

      // Make an Axios POST request to generate OTP
      axios
        .post(`http://localhost:8082/api/users/generateOTP?email=${email}`, {"email":email}) // Send the user's email to the backend
        .then((response) => {
        console.log(response.data.message);
        alert(response.data.message);
        setOtpSent(true);
        console.log("otpSent set to true"); 
      }) 
        .catch((error)=>{
      alert("An error occurred. Please try again later.");
      console.log(error);
    });
  };

  const handleValidateOTP = async () => {
      // Make an Axios POST request to validate OTP
      axios.post(`http://localhost:8082/api/users/validateOTP?email=${email}&otp=${otp}`, {
        email: email,
        otp: otp,
      }).then((response) => {
        console.log(response.data.message);
        console.log("otpValidated set to true"); 
        alert(response.data.message);
        navigate(`/reset-password?email=${email}`);
      })
      .catch((error)=>{
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
              <h6 className="header2">
                Enter your email to reset your password
              </h6>
            </div>
            <div className="card-body">
              {otpSent? (
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
                    >Validate OTP</button>
                </form>
              )
             : (
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
                  <button
                    type="submit"
                    className="btn btn-primary btn-block rounded-input"
                    onClick={handleSendOtp}
                  >
                    Send OTP
                  </button>
                </form>
              )}
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

export default ForgotPassword;
