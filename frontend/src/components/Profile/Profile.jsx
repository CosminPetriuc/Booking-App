/* eslint-disable no-unused-vars */
/*Profile.jsx*/
import {useEffect, useState} from "react";
import "./Profile.css";
import MenuBar from "../Home/MenuBar/MenuBar.jsx";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Profile = () => {
  const [activeIndex, setActiveIndex] = useState(0); //set the active index to 0 for Home icon
  const middleIcons = ["house", "stars", "geo-fill", "calendar2-check"];
  const bottomIcons = ["gear", "person"];
  const [myPreferences, setMyPreferences] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const allPreferences = [
    "PET_FRIENDLY",
    "MUSIC",
    "MUSEUM",
    "ART",
    "FOOD",
    "HIKING"
  ];
  const userId = 138;

  const [activeSection, setActiveSection] = useState("Sign in & Security"); // Default section

  const sections = [
    "Sign in & Security",
    "Account Preferences",
    "Privacy Settings",
    "Connected Devices",
    "Help & Support",
  ];

  useEffect(() => {
    const fetchPreferences = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/users/${userId}/preferences/getAllPreferences`);
        setMyPreferences(response.data); // Update preferences with the API response
        setLoading(false);
      } catch (err) {
        console.error("Error fetching preferences:", err);
        setError("Failed to load preferences");
        setLoading(false);
      }
    };

    fetchPreferences();
  }, [userId]);

  const handleSectionChange = (section) => {
    setActiveSection(section);
  };

  const handleAddPreference = async (preference) => {
    if (!myPreferences.includes(preference)) {
      try {
        const response = await axios.post(
            `http://localhost:8080/api/users/${userId}/preferences/addPreference`,
            [preference], // Backend expects an array
            {headers: {"Content-Type": "application/json"}}
        );
        setMyPreferences((prev) => [...prev, preference]); // Update state on success
      } catch (error) {
        console.error("Failed to add preference:", error);
      }
    }
  };

  const handleDeletePreference = async (preference) => {
    try {
      const response = await axios.delete(`http://localhost:8080/api/users/${userId}/preferences/deletePreference`, {
        data: [preference], // Backend expects an array in the request body
        headers: {"Content-Type": "application/json"},
      });
      setMyPreferences((prev) => prev.filter((pref) => pref !== preference)); // Update state on success
    } catch (error) {
      console.error("Failed to delete preference:", error);
    }
  };

  const navigate = useNavigate();
  const handleButtonClick = (index) => {
    setActiveIndex(index);

    if (index === 2) {
      navigate("/map");
    }

    if (index === 3) {
      navigate("/my-bookings");
    }

    if (index === 4) {
      navigate("/settings");
    }
  };

  return (
    <div className="main_container">
      <MenuBar initialActiveIndex={4} />
      <div className={`action_squares_container`}>
        <div className="Settings">
          <h3>Settings</h3>
          <div className="list_of_links">
            {sections.map((section, index) => (
              <div
                key={index}
                className={`icon-container ${
                  activeSection === section ? "active" : ""
                }`}
                onClick={() => handleSectionChange(section)}
              >
                {section}
              </div>
            ))}
          </div>
        </div>
        <div className="Change_container">
        {activeSection === "Sign in & Security" && (
          <div className="sign-in-security">
            <h2 className="section-title">
              <i className="bi bi-person-circle"></i> Sign in & security
            </h2>
            <div className="profile-section">
              <div className="avatar-container">
                <img
                  src="src/assets/cat.jpeg"
                  alt="User Avatar"
                  className="avatar"
                />
                <div className="avatar-buttons">
                  <button className="avatar-btn change-btn">Change</button>
                  <button className="avatar-btn remove-btn">Remove</button>
                </div>
              </div>
              <div className="form-section">
                <div className="form-left">
                  <label>First Name</label>
                  <input
                    type="text"
                    className="form-input"
                    placeholder="First Name"
                  />

                  <label>Last Name</label>
                  <input
                    type="text"
                    className="form-input"
                    placeholder="Last Name"
                  />
                </div>
                <div className="form-right">
                  <label>Location</label>
                  <select className="form-input-loc">
                    <option value="RO">Romania</option>
                    <option value="US">United States</option>
                    <option value="UK">United Kingdom</option>
                    <option value="CA">Canada</option>
                    <option value="FR">France</option>
                    <option value="DE">Germany</option>
                    <option value="IT">Italy</option>
                    <option value="ES">Spain</option>
                    <option value="AU">Australia</option>
                    <option value="BR">Brazil</option>
                    <option value="IN">India</option>
                    <option value="CN">China</option>
                    <option value="JP">Japan</option>
                    <option value="KR">South Korea</option>
                    <option value="MX">Mexico</option>
                    <option value="ZA">South Africa</option>
                    <option value="NG">Nigeria</option>
                  </select>

                  <label>Description</label>
                  <textarea
                    className="form-input-des"
                    placeholder="Write something"
                  ></textarea>
                </div>

                <div className="form-buttons">
                  <button className="cancel-btn"> Cancel </button>
                  <button className="update-btn"> Update </button>
                </div>
              </div>
            </div>
            <div className="password-section">
              <h3>Change Password</h3>
              <div className="form-group">
                <label>Current Password</label>
                <input
                  type="password"
                  className="form-input"
                  placeholder="Enter current password"
                />
              </div>
              <div className="form-group">
                <label>New Password</label>
                <input
                  type="password"
                  className="form-input"
                  placeholder="Enter new password"
                />
              </div>
              <div className="form-group">
                <label>Confirm New Password</label>
                <input
                  type="password"
                  className="form-input"
                  placeholder="Confirm new password"
                />
              </div>
              <button className="update-btn">Update Password</button>
            </div>
          </div>
        )}
          {activeSection === "Account Preferences" && (
            <div className="account-preferences">
              <h3>Account Preferences</h3>
              {loading ? (
                  <p>Loading preferences...</p>
              ) : error ? (
                  <p>{error}</p>
              ) : (
                  <>
              <div className="preferences-container my-preferences-container">
                <h4>Your Preferences:</h4>
                <div className="preferences-list">
                  {myPreferences.map((preference, index) => (
                    <div
                      key={index}
                      className="preference-item added"
                      onMouseEnter={(e) => e.target.classList.add("hovered")}
                      onMouseLeave={(e) => e.target.classList.remove("hovered")}
                    >
                      {preference}
                      <button
                        className="delete-btn"
                        onClick={() => handleDeletePreference(preference)}
                      >
                        X
                      </button>
                    </div>
                  ))}
                </div>
              </div>


              {/*All preferences section*/}
              <div className="preferences-container all-preferences-container">
                <h4>All Preferences:</h4>
                <div className="preferences-list">
                  {allPreferences.map((preference, index) => (
                    <div
                      key={index}
                      className={`preference-item ${
                        myPreferences.includes(preference) ? "selected" : ""
                      }`}
                      onClick={() => handleAddPreference(preference)}
                    >
                      {preference}
                    </div>
                  ))}
                </div>
              </div>
                  </>
              )}
            </div>
          )}
          {activeSection === "Privacy Settings" && (
            <div className="privacy-settings">
              <h3>Privacy Settings</h3>
              <p>Control who can see your information and manage your data.</p>
            </div>
          )}
          {activeSection === "Connected Devices" && (
            <div className="connected-devices">
              <h3>Connected Devices</h3>
              <p>View and manage devices linked to your account.</p>
            </div>
          )}
          {activeSection === "Help & Support" && (
            <div className="help-support">
              <h3>Help & Support</h3>
              <p>Get assistance or learn more about using this application.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;
