import React, { useState } from "react";
import "./SearchBarHotelPage.css";

const SearchBarHotelPage = () => {
  const [location, setLocation] = useState("");
  const [dates, setDates] = useState("");
  const [guests, setGuests] = useState(1);

  const handleSearch = () => {
    console.log("Searching for:", { location, dates, guests });
    // Add functionality to handle the search, like redirecting or filtering results
  };

  return (
    <div className="search-bar-container-details">
      <div className="search-item">
        <input
          type="text"
          placeholder="Anywhere"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
          className="input-field-details"
        />
      </div>
      <div className="search-item">
        <input
          type="text"
          placeholder="Anyweek"
          value={dates}
          onChange={(e) => setDates(e.target.value)}
          className="input-field-details"
        />
      </div>
      <div className="search-item">
        <input
          type="number"
          min="1"
          placeholder="Add Guests"
          value={guests}
          onChange={(e) => setGuests(e.target.value)}
          className="input-field-guests"
        />
      </div>
      <button className="search-button-details" onClick={handleSearch}>
        <i className="bi bi-search"></i> {/* Magnifying glass icon */}
      </button>
    </div>
  );
};

export default SearchBarHotelPage;
