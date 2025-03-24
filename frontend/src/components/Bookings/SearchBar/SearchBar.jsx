import React from "react";
import "./SearchBar.css";

const SearchBar = () => {
  return (
    <div className="search-bar-container">
      <div className="search-input">
        <input
          type="text"
          placeholder="Search destinations"
          className="input-field"
        />
      </div>
      <div className="search-dates">
        <input type="text" placeholder="Check-in" className="input-field" />
      </div>
      <div className="search-dates">
        <input type="text" placeholder="Check-out" className="input-field" />
      </div>
      <div className="search-guests">
        <input type="text" placeholder="Guests" className="input-field" />
      </div>
      <button className="search-button">
        <i class="bi bi-search"></i>
      </button>
    </div>
  );
};

export default SearchBar;
