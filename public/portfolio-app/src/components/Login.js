import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../App.css";
import personIcon from "./pngegg.png";
import axios from "axios";

function Login() {
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card rounded-input login-form">
            <div className="card-header">
              <img src={personIcon} alt="Person Icon" className="person-icon" />
              <h3 className="header1">Welcome back!</h3>
              <h6 className="header2">Sign in to your account</h6>
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
                {/* <button
                  type="submit"
                  className="btn btn-primary btn-block rounded-input"
                >
                  Login
                </button> */}
                <Link to="/home" type="submit" className="btn btn-primary btn-block rounded-input">Login</Link>
              </form>
            </div>
            <div className="card-footer text-center">
              <small className="text-muted">
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
