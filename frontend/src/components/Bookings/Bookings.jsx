import React from "react";
import { useNavigate } from "react-router-dom";
import "./Bookings.css";

import MenuBar from "../Home/MenuBar/MenuBar.jsx";
import SearchBar from "../Bookings/SearchBar/SearchBar.jsx";
import HotelCardCarousel from "../Bookings/HotelCardCarousel/HotelCardCarousel.jsx";

const Bookings = () => {
  return (
    <div className="bookings-container">
      <MenuBar initialActiveIndex={2} /> {/* Highlight Map icon */}
      <div className="content-container">
        <SearchBar /> {/* Search bar */}
        <HotelCardCarousel /> {/* Card carousel */}
      </div>
    </div>
  );
};

export default Bookings;
