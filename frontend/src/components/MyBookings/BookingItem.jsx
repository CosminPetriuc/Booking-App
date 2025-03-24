import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./BookingItem.css";

const BookingItem = ({ userId }) => {
  const [bookings, setBookings] = useState([]); // Initialize as an empty array
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the bookings from the backend based on the userId
    const fetchBookings = async () => {
      try {
        const response = await axios.get(
            `http://localhost:8080/api/bookings/get_upcoming_bookings_by_userID?userID=${138}`
        );
        // Ensure that the response data is an array
        setBookings(Array.isArray(response.data) ? response.data : []);
      } catch (err) {
        setError("Failed to fetch bookings");
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, [userId]);

  const handleClick = (bookingId) => {
    if (bookingId) {
      navigate(`/bookings/hotel/${bookingId}`);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (error) {
    return <div className="error">{error}</div>;
  }

  return (
      <div className="booking-list">
        {bookings.length === 0 ? (
            <div>No upcoming bookings found</div>
        ) : (
            bookings.map((booking) => (
                <div
                    className="booking-item"
                    key={booking.bookingId}
                    onClick={() => handleClick(booking.bookingId)}
                >
                  <h1 className="hotel-title">{booking.hotelName}</h1>
                  <div className="hotel-photo">
                    <div className="booking-details-rectangle">
                      {/* Column #1: City or Destination */}
                      <div className="detail-column">
                        <div className="detail-heading">
                          <svg
                              width="24"
                              height="24"
                              fill="none"
                              stroke="currentColor"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              viewBox="0 0 24 24"
                              aria-hidden="true"
                          >
                            <path d="M12 10.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5z"></path>
                            <path d="M19 10.5c0 7-7 12-7 12s-7-5-7-12a7 7 0 0 1 14 0z"></path>
                          </svg>
                          <span>City or Destination</span>
                        </div>
                        <div className="detail-value">
                          {booking.destination || "Unknown Destination"}
                        </div>
                      </div>

                      {/* Column #2: Booking Dates */}
                      <div className="detail-column">
                        <div className="detail-heading">
                          <svg
                              width="24"
                              height="24"
                              fill="none"
                              stroke="currentColor"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              viewBox="0 0 24 24"
                              aria-hidden="true"
                          >
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                          </svg>
                          <span>Booking Dates</span>
                        </div>
                        <div className="detail-value">
                          {booking.startDate && booking.endDate
                              ? `${booking.startDate} to ${booking.endDate}`
                              : booking.bookingDates || "Dates not available"}
                        </div>
                      </div>

                      {/* Column #3: Guests & Rooms */}
                      <div className="detail-column">
                        <div className="detail-heading">
                          <svg
                              width="24"
                              height="24"
                              fill="none"
                              stroke="currentColor"
                              strokeWidth="2"
                              strokeLinecap="round"
                              strokeLinejoin="round"
                              viewBox="0 0 24 24"
                              aria-hidden="true"
                          >
                            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                            <circle cx="9" cy="7" r="4"></circle>
                            <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                            <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                          </svg>
                          <span>Guests & Rooms</span>
                        </div>
                        <div className="detail-value">
                          {booking.adultsNumber && booking.kidsNumber
                              ? `${booking.adultsNumber} Adults, ${booking.kidsNumber} Kids`
                              : booking.guestsRooms || "Guests info not available"}
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
            ))
        )}
      </div>
  );
};

BookingItem.propTypes = {
  userId: PropTypes.number.isRequired,
};

export default BookingItem;
