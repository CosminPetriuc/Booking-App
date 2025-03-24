import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Home.css";

import MenuBar from "./MenuBar/MenuBar.jsx";
import AISquare from "./AISquare/AISquare.jsx";
import MapSquare from "./MapSquare/MapSquare.jsx";
import ProfileSquare from "./ProfileSquare/ProfileSquare.jsx";
import BookingSquare from "./BookingSquare/BookingSquare.jsx";

const Home = () => {
  const [activeSquare, setActiveSquare] = useState(false); // Manage AI Square state
  const location = useLocation(); // Get location to read state

  // Effect to handle state passed from navigation
  useEffect(() => {
    if (location.state?.expandAssistant) {
      setActiveSquare(true); // Expand the AI Square if state indicates it
      location.state.expandAssistant = false; // Clear state
    } else {
      setActiveSquare(false);
    }

  }, [location.state]);

  const handleAIActivate = () => {
    setActiveSquare((prev) => !prev); // Toggle active state
  };

  return (
      <div className="main-container">
        <MenuBar onAIActivate={handleAIActivate} />
        <div className="action-squares-container">
          <AISquare
              activeSquare={activeSquare}
              setActiveSquare={setActiveSquare}
          />
          <MapSquare />
          <ProfileSquare />
          <BookingSquare />
        </div>
      </div>
  );
};

export default Home;
