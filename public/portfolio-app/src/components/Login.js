import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Login.css";
import "../App.css"
import personIcon from "./pngegg.png";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Swal from 'sweetalert2';

function Login() {
  const navigate = useNavigate();
  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent the default form submission behavior
  
    // Get the email and password from the input fields (replace with actual field IDs)
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // Make a POST request to your API endpoint
    axios.post("http://localhost:8082/api/users/login", {
      email: email,
      password: password,
    })
    .then((response) => {
      sessionStorage.setItem('userData', JSON.stringify(response.data.data));
      console.log(response.data);
    if (response.data.message != null) {
      Swal.fire({
        icon: 'success',
        title: 'Welcome!',
        text: response.data.message,
        footer: ''
      });
      navigate("/home");
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: response.data.message,
        footer: 'Try Again!'
      });
    }
    })
    .catch((error) => {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: error,
        footer: 'Try Again!'
      });
    console.error(error);
    });

      ;
  
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
          <div className="card login-form" style={componentStyles}>
            <div className="card-header" style={componentStyles}>
              <img src={personIcon} alt="Person Icon" style={personIconStyles} />
              <h3 style={header1Styles}>Welcome back!</h3>
              <h6 style={header2Styles}>Sign in to your account</h6>
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
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <input
                    type="password"
                    className="form-control rounded-input"
                    id="password"
                    placeholder="Enter your password"
                  />
                </div>
                <button type="submit" className="btn btn-primary btn-block rounded-input" onClick={handleLogin}>
                  Login
                </button>
              </form>
            </div>
            <div className="card-footer text-center">
              <small className="header2">
                Don't have an account?{" "}
                <Link to="/signup" className="text-primary">
                  Sign Up
                </Link>
              </small>
              <br />
              <small>
                <a href="forgot-password" className="text-primary">
                  Forgot Password?
                </a>
              </small>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
