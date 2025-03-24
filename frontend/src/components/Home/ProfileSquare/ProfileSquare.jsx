import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./ProfileSquare.css";

const ProfileSquare = () => {
  const [preferences, setPreferences] = useState([]); // State to hold preferences
  const [loading, setLoading] = useState(true); // State to manage loading state
  const [error, setError] = useState(null); // State to manage errors
  const userId = 138; // Replace with dynamic user ID as needed

  const navigate = useNavigate();

  useEffect(() => {
    const fetchPreferences = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/${userId}/preferences/getAllPreferences`);
        setPreferences(response.data); // Update preferences with the API response
        setLoading(false);
      } catch (err) {
        console.error("Error fetching preferences:", err);
        setError("Failed to load preferences");
        setLoading(false);
      }
    };

    fetchPreferences();
  }, [userId]);

  return (
    <div className="profile-square">
      <div className="profile-icons-container">
        <div className="icon-container">
          <div className="icon">
            <i className="bi bi-person icon user-icon"></i>
          </div>
        </div>
      </div>
      <div className="profile-content-container">
        <div className="profile-content-elements">
          <div className="content-text">
            <div>Preferences</div>
            <i
              className="bi bi-pencil-square"
              onClick={() => navigate("/settings")}
            ></i>
          </div>
          <div className="preferences-content">
            {loading && <div>Loading...</div>}
            {error && <div>{error}</div>}
            {!loading &&
                !error &&
              preferences.map((preference, idx) => (
              <button key={idx} className="btn btn-dark custom-btn">
                {preference}
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
export default ProfileSquare;
