import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../App.css";
import axios from "axios";

function ForgotPassword() {
    const [email, setEmail] = useState(""); // Define and initialize 'email'
    const navigate = useNavigate();

  const handleSendOtp = async (e) => {
    e.preventDefault();
      // Make an Axios POST request to generate OTP
      axios
        .post(`http://localhost:8082/api/users/generateOTP?email=${email}`, {"email":email}) // Send the user's email to the backend
        .then((response) => {
        console.log(response.data.message);
        console.log(email)
        alert(response.data.message);
        navigate(`/validate-otp?email=${email}`);
      }) 
        .catch((error)=>{
      alert("An error occurred. Please try again later.");
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
