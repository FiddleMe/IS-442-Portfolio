import React from 'react';
import '../App.css';
import {Link} from "react-router-dom";

function SignUp() {
  return (
    <div className="container mt-5">
      <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card rounded-input login-form">
                    <div class="card-header">
                        <img src="./images/pngegg.png" alt="Person Icon" class="person-icon"></img>
                        <h3 class="header1">Create Account</h3>
                        <h6 class="header2">Sign up for a new account</h6>
                    </div>
                    <div class="card-body">
                        <form>
                            <div class="form-group">
                                <label for="first_name">First Name</label>
                                <input type="text" class="form-control rounded-input" id="first_name" placeholder="Enter your first name"></input>
                            </div>
                            <div class="form-group">
                                <label for="last_name">Last Name</label>
                                <input type="text" class="form-control rounded-input" id="last_name" placeholder="Enter your last name"></input>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control rounded-input" id="email" placeholder="Enter your email"></input>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control rounded-input" id="password" placeholder="Enter your password"></input>
                            </div>

                            <div class="form-group">
                                <label for="confirm_password">Confirm Password</label>
                                <input type="password" class="form-control rounded-input" id="confirm_password" placeholder="Confirm your password"></input>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block rounded-input">Create Account</button>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <small class="text-muted">
                            Already have an account? <Link to="/login" className="text-primary">Login</Link>
                        </small>
                    </div>
                </div>

            </div>
        </div>
    </div>
  );
}

export default SignUp;
