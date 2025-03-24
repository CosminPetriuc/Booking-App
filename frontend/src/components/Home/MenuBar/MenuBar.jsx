import { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

import "./MenuBar.css";
import "../Home.css";

const MenuBar = ({ initialActiveIndex = 0, onAIActivate }) => {
  const [activeIndex, setActiveIndex] = useState(initialActiveIndex); // Default to Home icon
  const [activeSquare, setActiveSquare] = useState(false); // AI Assistant activation state
  const middleIcons = ["house", "stars", "geo-fill", "calendar2-check"];
  const bottomIcons = ["gear"];
  const navigate = useNavigate();
  const location = useLocation();

  const handleButtonClick = (index) => {
    if (activeIndex !== index) {
      setActiveIndex(index); // Update active index if different
    }

    if (index === 0) {
      navigate("/home"); // Navigate to the Home page
    }

    if (index === 1) {
      if (location.pathname !== "/home") {
        // If not on the Home page, navigate and activate the AI Assistant
        navigate("/home", { state: { expandAssistant: true } });
      } else {
        // If already on the Home page, toggle the AI Assistant
        setActiveSquare(!activeSquare);
        if (onAIActivate) onAIActivate(!activeSquare); // Notify parent component if needed
      }
    }

    if (index === 2) {
      navigate("/map"); // Navigate to the Map page
    }

    if (index === 3) {
      navigate("/my-bookings"); // Navigate to My Bookings page
    }

    if (index === 4) {
      navigate("/settings"); // Navigate to Settings page
    }
  };

  useEffect(() => {
    if (location.pathname === "/home" && location.state?.expandAssistant) {
      setActiveSquare(true); // Activate AI Assistant
      if (onAIActivate) onAIActivate(true); // Notify parent component
      location.state.expandAssistant = false; // Clear state to prevent repeated activation
    }
  }, [location, onAIActivate]);

  return (
      <div className="menu-bar-container">
        <div className="middle-strip">
          {middleIcons.map((icon, index) => (
              <button
                  key={index}
                  className={`menu-item ${activeIndex === index ? "active" : ""}`}
                  onClick={() => handleButtonClick(index)}
              >
                <i className={`bi bi-${icon}`}></i>
              </button>
          ))}
        </div>

        <div className="bottom-strip">
          {bottomIcons.map((icon, index) => (
              <button
                  key={index}
                  className={`menu-item ${activeIndex === index + 4 ? "active" : ""}`}
                  onClick={() => handleButtonClick(index + 4)}
              >
                <i className={`bi bi-${icon}`}></i>
              </button>
          ))}
        </div>
      </div>
  );
};

export default MenuBar;
