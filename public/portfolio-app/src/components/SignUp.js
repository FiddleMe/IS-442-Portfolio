import React, { useState } from "react";
import "./Login.css";
import { Link } from "react-router-dom";
import personIcon from "./pngegg.png";
import axios from "axios";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';

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
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Password and confirm password do not match!',
        footer: 'Try Again!'
      });
      return;
    }
    const param = {
      "recipient": user.email,
      "subject": "Your Account Has Been Successfully Created",
      "msg": "Dear "+ user.firstName + " " + user.lastName +", \n\n\nThank you for joining us. We are pleased to confirm that your account has been created successfully. \nKindly note that this is a system-generated email. Please do not reply. \n\n\n Best Regards, \n Team 3"
    }
    // Make an HTTP POST request to create a new user using Axios
    axios
      .post("http://localhost:8082/api/users", user)
      .then((response) => {
        console.log(response.data.message);
        var message = response.data.message;
        if (response.data != null) {
          Swal.fire({
            icon: 'success',
            title: 'Account Created!',
            text: response.data.message,
            footer: ''
          });
          // Redirect to the login page or perform any other action
          return axios.post("http://localhost:8082/api/email/sendEmail", param)
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
            footer: 'Try Again!'
          });
        }
      })
      .then (response => {
        console.log('Email request successful:', response.data);
        navigate("/login");
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
            <div className="card-header" style={componentStyles}>
              <img
                src={personIcon}
                alt="Person Icon"
                style={personIconStyles}
              />
              <h3 style={header1Styles}>Create Account</h3>
              <h6 style={header2Styles}>Sign up for a new account</h6>
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
              <small className="header2">
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
