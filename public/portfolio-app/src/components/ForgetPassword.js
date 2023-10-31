import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import axios from "axios";
import Swal from 'sweetalert2';

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
        Swal.fire({
          icon: 'success',
          title: 'Nice!',
          text: response.data.message,
          footer: ''
        });
        navigate(`/validate-otp?email=${email}`);
      }) 
        .catch((error)=>{
      alert("An error occurred. Please try again later.");
      console.log(error);
    });
  };
  const componentStyles = {
    backgroundColor: "#101729", // Background color
    borderRadius: "25px", // Border radius
    padding: "20px", // Padding
    textAlign: "center", // Text alignment
    position: "relative", // Position
  };

  const personIconStyles = {
    width: "80px",
    borderRadius: "50%",
    marginBottom: "10px",
    backgroundColor: "#E1E5F8",
    zIndex: 1,
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
              <h6 style={header2Styles}>
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

export default ForgotPassword;
