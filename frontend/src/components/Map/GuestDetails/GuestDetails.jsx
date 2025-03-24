import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate
import "./GuestDetails.css";

const GuestDetails = ({ selectedCity, onCityChange }) => {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [guests, setGuests] = useState({ adults: 1, kids: 0, pets: 0 });

  const navigate = useNavigate(); // Initialize useNavigate hook

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setGuests({ ...guests, [name]: value });
  };

  const handleSearch = () => {
    console.log("Search Details:");
    console.log("City:", selectedCity || "No city selected");
    console.log("Start Date:", startDate);
    console.log("End Date:", endDate);
    console.log("Guests:", guests);

    // Redirect to /bookings page with query parameters
    navigate(
      `/bookings?city=${selectedCity}&startDate=${startDate}&endDate=${endDate}&adults=${guests.adults}&kids=${guests.kids}&pets=${guests.pets}`
    );
  };

  return (
    <div className="guest-details">
      <h2>Plan Your Trip</h2>
      <div className="city-picker">
        <label>
          Select City:
          <select
            value={selectedCity}
            onChange={(e) => onCityChange(e.target.value)}
          >
            <option value="">-- Choose a City --</option>
            <option value="London">London</option>
            <option value="Paris">Paris</option>
            <option value="Berlin">Berlin</option>
            <option value="Rome">Rome</option>
            <option value="Madrid">Madrid</option>
            <option value="Vienna">Vienna</option>
            <option value="Amsterdam">Amsterdam</option>
            <option value="Lisbon">Lisbon</option>
            <option value="Dublin">Dublin</option>
            <option value="Prague">Prague</option>
            <option value="Budapest">Budapest</option>
            <option value="Athens">Athens</option>
          </select>
        </label>
      </div>
      <div className="date-picker">
        <label>
          Start Date:
          <input
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
        </label>
        <label>
          End Date:
          <input
            type="date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </label>
      </div>
      <div className="guest-picker">
        <label>
          Adults:
          <input
            type="number"
            name="adults"
            min="1"
            value={guests.adults}
            onChange={handleInputChange}
          />
        </label>
        <label>
          Kids:
          <input
            type="number"
            name="kids"
            min="0"
            value={guests.kids}
            onChange={handleInputChange}
          />
        </label>
        <label>
          Pets:
          <input
            type="number"
            name="pets"
            min="0"
            value={guests.pets}
            onChange={handleInputChange}
          />
        </label>
      </div>
      <button className="search-button-2" onClick={handleSearch}>
        Search
      </button>
    </div>
  );
};

export default GuestDetails;
