import React, { useState } from "react";
import "./Login.css";
import { Link } from "react-router-dom";
import personIcon from "./pngegg.png";
import axios from "axios";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function SignUp() {
  const location = useLocation();
  const navigate = useNavigate();
  const [user, setUser] = useState({
    First_Name: "",
    Last_Name: "",
    Email: "",
    Password: "",
    confirmPassword: "",
  });

  const createUser = () => {
    if (user.password !== user.confirmPassword) {
      alert("Password and Confirm Password must match.");
      return;
    }

    // Make an HTTP POST request to create a new user using Axios
    axios
      .post("http://localhost:8082/api/users", user)
      .then((response) => {
        console.log(response.data.message);
        var message = response.data.message;
        if (response.data != null) {
          alert(message);
          // Redirect to the login page or perform any other action
          navigate("/login");
        } else {
          alert("Failed to create user. Please try again.");
        }
      })
      .catch((error) => {
        alert("An error occurred. Please try again later.");
        console.error(error);
      });
  };
  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card rounded-input login-form">
            <div className="card-header">
              <img
                src={personIcon}
                alt="Person Icon"
                className="person-icon"
              ></img>
              <h3 className="header1">Create Account</h3>
              <h6 className="header2">Sign up for a new account</h6>
            </div>
            <div className="card-body">
              <form
                onSubmit={(e) => {
                  e.preventDefault();
                  createUser();
                }}
              >
                <div className="form-group">
                  <label htmlFor="first_name">First Name</label>
                  <input
                    type="text"
                    className="form-control rounded-input"
                    id="first_name"
                    placeholder="Enter your first name"
                    value={user.firstName}
                    onChange={(e) =>
                      setUser({ ...user, firstName: e.target.value })
                    }
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="last_name">Last Name</label>
                  <input
                    type="text"
                    className="form-control rounded-input"
                    id="last_name"
                    placeholder="Enter your last name"
                    value={user.lastName}
                    onChange={(e) =>
                      setUser({ ...user, lastName: e.target.value })
                    }
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input
                    type="email"
                    className="form-control rounded-input"
                    id="email"
                    placeholder="Enter your email"
                    value={user.email}
                    onChange={(e) =>
                      setUser({ ...user, email: e.target.value })
                    }
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="password">Password</label>
                  <input
                    type="password"
                    className="form-control rounded-input"
                    id="password"
                    placeholder="Enter your password"
                    value={user.password}
                    onChange={(e) =>
                      setUser({ ...user, password: e.target.value })
                    }
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="confirm_password">Confirm Password</label>
                  <input
                    type="password"
                    className="form-control rounded-input"
                    id="confirm_password"
                    placeholder="Confirm your password"
                    value={user.confirmPassword}
                    onChange={(e) =>
                      setUser({ ...user, confirmPassword: e.target.value })
                    }
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block rounded-input"
                >
                    Create Account
                </button>
              </form>
            </div>
            <div className="card-footer text-center">
              <small className="text-muted">
                Already have an account?{" "}
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

export default SignUp;
