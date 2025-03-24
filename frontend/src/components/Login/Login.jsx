import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import AppleIcon from "@mui/icons-material/Apple";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevent page reload on form submission

    try {
      const response = await fetch(
          `http://localhost:8080/api/auth/login?email=${encodeURIComponent(
              email
          )}&password=${encodeURIComponent(password)}`,
          {
            method: "POST",
          }
      );

      if (response.ok) {
        const data = await response.json();
        alert(`Login successful! User ID: ${data.userId}`);
        // Redirect to the home page
        navigate("/home");
      } else {
        const errorText = await response.text();
        alert(`Login failed: ${errorText}`);
      }
    } catch (error) {
      console.error("Error logging in:", error);
      alert("An error occurred while logging in. Please try again later.");
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
            <h1>Login</h1>
            <form className="form-container" onSubmit={handleSubmit}>
              <input
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
              />
              <div className="password-field">
                <input
                    type="password"
                    placeholder="Enter your password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <span className="show-password"></span>
              </div>
              <p className="forgot-password">Forgot password?</p>
              <button type="submit" className="sign-in-button">
                Sign in
              </button>
            </form>
            <p className="switch-form">
              Are you new?{" "}
              <Link to="/register" className="create-account-link">
                Create an account
              </Link>
            </p>
            <div className="divider">Or sign in with</div>
            <div className="social-login">
              <button className="google-button">
                <i className="bi bi-google"></i>Google
              </button>
              <button className="apple-button">
                <AppleIcon style={{ marginRight: "8px" }}></AppleIcon>Apple
              </button>
            </div>
          </div>
        </div>
      </div>
  );
};

export default Login;
