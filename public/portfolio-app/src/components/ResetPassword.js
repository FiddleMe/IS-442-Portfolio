import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../App.css";
import axios from "axios";
import { useLocation } from "react-router-dom";

function ResetPassword() {
  const location = useLocation();
  const email = new URLSearchParams(location.search).get("email");
  const [newPassword, setNewPassword] = useState(""); // Define and initialize 'newPassword'
  const [confirmPassword, setConfirmPassword] = useState(""); // Define and initialize 'confirmPassword'

  const handleResetPassword = async () => {
    if (newPassword === confirmPassword) {
      axios
        .put("http://localhost:8082/api/users/updatePassword", {
          email: email,
          password: newPassword,
        })
        .then((response) => {
          if (response.data.message != null) {
            alert(response.data.message);
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
  return (
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
  );
}

export default ResetPassword;
