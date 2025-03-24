// Adapted HotelDetails component
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./HotelDetails.css";
import axios from "axios";

const HotelDetails = () => {
  const { hotelId } = useParams();
  const [hotel, setHotel] = useState(null);
  const [checkInDate, setCheckInDate] = useState(null);
  const [checkOutDate, setCheckOutDate] = useState(null);
  const [mainImage, setMainImage] = useState(null);

  useEffect(() => {
    const fetchHotelDetails = async () => {
      try {
        const response = await axios.get(`/api/hotel/get_hotel_by_id?hotelID=${hotelId}`);
        setHotel(response.data);
        setMainImage(response.data.images[0]); // Default to the first image
      } catch (error) {
        console.error("Error fetching hotel details:", error);
      }
    };

    fetchHotelDetails();
  }, [hotelId]);

  if (!hotel) {
    return (
        <div className="error-container">
          <h2>Hotel not found</h2>
          <p>We couldn't find details for this hotel. Please try again.</p>
        </div>
    );
  }

  const calculateTotalNights = () => {
    if (checkInDate && checkOutDate) {
      const timeDiff = Math.abs(checkOutDate - checkInDate);
      return Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
    }
    return 0;
  };

  const totalNights = calculateTotalNights();

  const otherImages = hotel.images.filter((image) => image !== mainImage);

  return (
      <div className="hotel-details-container">
        <div className="images-display">
          <div className="main-image-container">
            <img src={mainImage} alt="Main Hotel" className="main-image" />
          </div>

          <div className="grid-images-container">
            {otherImages.map((image, index) => (
                <img
                    key={index}
                    src={image}
                    alt={`Hotel Image ${index + 1}`}
                    className="grid-image"
                    onClick={() => setMainImage(image)}
                />
            ))}
          </div>
        </div>

        <div className="hotel-details-wrapper">
          <div className="hotel-info-container">
            <h1 className="hotel-name">{hotel.name}</h1>
            <p className="hotel-meta">{hotel.meta || "Details not available."}</p>
            <h2 className="hotel-section-title">Description</h2>
            <p className="hotel-description">{hotel.description}</p>
            <button className="show-more">Show more &gt;</button>
          </div>

          <div className="reserve-container">
            <p className="hotel-price">
              <span className="price-amount">{hotel.pricePerNight || "N/A"}</span> night
            </p>
            <div className="booking-dates">
              <div className="date-picker-container">
                <label>Check-in</label>
                <DatePicker
                    selected={checkInDate}
                    onChange={(date) => setCheckInDate(date)}
                    dateFormat="dd/MM/yyyy"
                    placeholderText="Select a date"
                    minDate={new Date()}
                />
              </div>
              <div className="date-picker-container">
                <label>Check-out</label>
                <DatePicker
                    selected={checkOutDate}
                    onChange={(date) => setCheckOutDate(date)}
                    dateFormat="dd/MM/yyyy"
                    placeholderText="Select a date"
                    minDate={checkInDate || new Date()}
                />
              </div>
            </div>
            <div className="guests-dropdown">
              <label>GUESTS</label>
              <select>
                <option>2 guests</option>
              </select>
            </div>
            <button className="reserve-button">Reserve</button>
            <div className="booking-summary">
              <p>
                {hotel.pricePerNight || "N/A"} × {totalNights} nights <span>€{(hotel.pricePerNight || 0) * totalNights}</span>
              </p>
              <p>
                <strong>Total</strong> <span>€{(hotel.pricePerNight || 0) * totalNights}</span>
              </p>
            </div>
          </div>
        </div>
      </div>
  );
};

export default HotelDetails;
