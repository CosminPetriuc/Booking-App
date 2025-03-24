import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom"; // Import useNavigate
import "./Register.css";

import eyeIcon from "../../assets/iconoir_eye-solid.svg";

const Register = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState({});
  const navigate = useNavigate(); // Initialize useNavigate

  // Regex patterns
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  const passwordRegex =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  const handleSubmit = (e) => {
    e.preventDefault();
    let formErrors = {};

    // Validate first and last name
    if (!firstName || !lastName) {
      formErrors.name = "First and Last name are required.";
    }

    // Validate email
    if (!emailRegex.test(email)) {
      formErrors.email = "Please enter a valid email address.";
    }

    // Validate password
    if (!passwordRegex.test(password)) {
      formErrors.password =
        "Password must be at least 8 characters, include at least one letter, one number, and one special character.";
    }

    if (Object.keys(formErrors).length === 0) {
      // Store user data in localStorage (simulation)
      const newUser = { firstName, lastName, email, password };
      let users = JSON.parse(localStorage.getItem("users")) || [];
      users.push(newUser); // Add new user
      localStorage.setItem("users", JSON.stringify(users)); // Save all users

      // Redirect to the login page
      navigate("/");
    } else {
      setErrors(formErrors);
    }
  };

  return (
    <div className="main-container">
      <div className="login-container">
        {/* Image section */}
        <div className="image-section">
          <div className="text-box">
            <p className="first-paragraph">Capturing moments,</p>
            <p className="second-paragraph">Creating memories</p>
          </div>
        </div>

        {/* Form section */}
        <div className="form-section">
          <h1>Create an account</h1>
          <form onSubmit={handleSubmit}>
            <div className="name-fields">
              <input
                type="text"
                placeholder="First Name"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
              />
              <input
                type="text"
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
              />
            </div>
            {errors.name && <span className="error">{errors.name}</span>}
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            {errors.email && <span className="error">{errors.email}</span>}
            <div className="password-field">
              <input
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              {/* <span className="show-password">
                <img src={eyeIcon} alt="Show password icon" />
              </span> */}
            </div>
            {errors.password && (
              <span className="error">{errors.password}</span>
            )}
            <div className="terms">
              <input type="checkbox" id="terms" required />
              <label htmlFor="terms">
                I agree to the{" "}
                <a href="/terms" className="terms-link">
                  Terms & Conditions
                </a>
              </label>
            </div>
            <button type="submit" className="sign-in-button">
              Create account
            </button>
          </form>
          <p className="switch-form">
            Already have an account? <Link to="/">Log in</Link>
          </p>
          <div className="divider">Or register with</div>
          <div className="social-login">
            <button className="google-button">Google</button>
            <button className="apple-button">Apple</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
