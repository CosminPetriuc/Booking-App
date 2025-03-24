import React from "react";
import MenuBar from "../Home/MenuBar/MenuBar";
import "./MyBookings.css";
import BookingItem from "./BookingItem";

const MyBookings = () => {
  return (
    <div className="my-bookings-page">
      <MenuBar initialActiveIndex={3} /> {/* Highlight MyBookings icon */}
      {/* Background Rectangle */}
      <div className="background-rectangle">
        {/* App header / icon / etc. inside the pink rectangle */}
        <div className="app-header">
          <div className="icon">
            <div className="inner-circle">
              {/* If you have an actual small SVG or image, put it here */}
              <div className="inner-circle">
                <svg
                  width="20"
                  height="20"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                >
                  <circle cx="12" cy="12" r="10"></circle>
                  <line x1="2" y1="12" x2="22" y2="12"></line>
                  <path d="M12 2a15.3 15.3 0 0 1 0 20"></path>
                  <path d="M12 2a15.3 15.3 0 0 0 0 20"></path>
                </svg>
              </div>
            </div>
          </div>
          <div className="app-name">App Name</div>
        </div>
        {/* The white(ish) rectangle that holds the title + scrollable content */}
        <div className="inside-rectangle">
        <div className="scroll-container">
          <h2 className="my-bookings-title">My Bookings</h2>
            {/*  Render your booking item(s) here  */}
            <BookingItem />
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyBookings;
