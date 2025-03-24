import React from "react";
import { useParams } from "react-router-dom";
import "./HotelPage.css";

import MenuBar from "../Home/MenuBar/MenuBar.jsx";
import SearchBarHotelPage from "./SearchBarHotelPage/SearchBarHotelPage.jsx";
import HotelDetails from "./HotelDetails/HotelDetails.jsx";

const HotelPage = () => {
  const { hotelId } = useParams(); // Extract the hotelId from the route parameters

  return (
    <div className="hotel-page-container">
      <MenuBar initialActiveIndex={2} /> {/* Highlight Map icon */}
      <div className="details-container">
        <SearchBarHotelPage />
        <div className="hotel-details-wrapper">
          {/* Pass the hotelId as a prop to HotelDetails */}
          <HotelDetails hotelId={hotelId} />
        </div>
      </div>
    </div>
  );
};

export default HotelPage;
