import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Login.css";
import axios from "axios";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function ResetPassword() {
  const location = useLocation();
  const navigate = useNavigate();
  const email = new URLSearchParams(location.search).get("email");
  console.log(email);
  const [newPassword, setNewPassword] = useState(""); // Define and initialize 'newPassword'
  const [confirmPassword, setConfirmPassword] = useState(""); // Define and initialize 'confirmPassword'

  const handleResetPassword = async (e) => {
    e.preventDefault();
    if (newPassword === confirmPassword) {
      axios
        .put("http://localhost:8082/api/users/updatePassword", {
          email: email,
          password: newPassword,
        })
        .then((response) => {
            console.log(response.data);
          if (response.data.message != null) {
            alert(response.data.message);
            navigate("/login");
          } else {
            alert("Failed to reset the password. Please try again.");
          }
        })
        .catch((error) => {
          alert("An error occurred. Please try again later.");
          console.error(error);
        });
    } else {
      alert("Password and Confirm Password must match.");
    }
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
              <h6 style={header2Styles}>Enter new password for your account</h6>
            </div>
            <div className="card-body">
              <form>
                <div className="form-group">
                  <label htmlFor="newPassword">New Password</label>
                  <input
                    type="password"
                    className="form-control rounded-input"
                    id="newPassword"
                    placeholder="Enter new password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="confirmPassword">Confirm Password</label>
                  <input
                    type="password"
                    className="form-control rounded-input"
                    id="confirmPassword"
                    placeholder="Confirm new password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                  />
                </div>
                <button
                  type="submit"
                  className="btn btn-primary btn-block rounded-input"
                  onClick={handleResetPassword}
                >
                  Reset Password
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

export default ResetPassword;
