import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./HotelCardCarousel.css";

// Function to generate hotel data dynamically
const generateHotels = (count) => {
  const images = [
    "src/assets/HotelCardCarousel/hotel1.jpg",
    "src/assets/HotelCardCarousel/hotel2.jpg",
    "src/assets/HotelCardCarousel/hotel3.jpg",
  ];
  const names = [
    "Austria",
    "France",
    "Italy",
    "Germany",
    "Spain",
    "USA",
    "UK",
    "Japan",
    "India",
  ];
  const hotels = [];

  for (let i = 0; i < count; i++) {
    hotels.push({
      id: i, // Unique ID for each hotel
      name: `Hotel, ${names[i % names.length]}`, // Rotate through country names
      price: `€${Math.floor(150 + Math.random() * 100)}/night`, // Randomized prices
      rating: (4 + Math.random()).toFixed(2), // Randomized ratings
      images, // Array of images for each hotel
    });
  }

  return hotels;
};

// eslint-disable-next-line react/prop-types
  const HotelCard = ({ hotel }) => {
  const [currentImageIndex, setCurrentImageIndex] = useState(0); // Local state for image index
  const navigate = useNavigate();

  const handleCardClick = () => {
    // eslint-disable-next-line react/prop-types
    navigate(`/bookings/hotel/${hotel.id}`); // Navigate to the HotelPage
  };

  const handleArrowClick = (e, direction) => {
    e.stopPropagation(); // Prevent event from propagating to the card click
    const newIndex =
      direction === "next"
          // eslint-disable-next-line react/prop-types
        ? (currentImageIndex + 1) % hotel.images.length
          // eslint-disable-next-line react/prop-types
        : (currentImageIndex - 1 + hotel.images.length) % hotel.images.length;
    setCurrentImageIndex(newIndex); // Update the local state for image index
  };

  return (
    <div className="hotel-card" onClick={handleCardClick}>
      <div className="image-container">
        <img
          src={hotel.images[currentImageIndex]} // Use the current image based on index
          alt={hotel.name}
          className="hotel-image"
        />
        <button
          className="prev-button"
          onClick={(e) => handleArrowClick(e, "prev")} // Go to previous image
        >
          ❮
        </button>
        <button
          className="next-button"
          onClick={(e) => handleArrowClick(e, "next")} // Go to next image
        >
          ❯
        </button>
      </div>
      <div className="hotel-info">
        {/* eslint-disable-next-line react/prop-types */}
        <h3 className="hotel-name">{hotel.name}</h3>
        {/* eslint-disable-next-line react/prop-types */}
        <p className="hotel-price">{hotel.price}</p>
        {/* eslint-disable-next-line react/prop-types */}
        <p className="hotel-rating">★ {hotel.rating}</p>
      </div>
    </div>
  );
};

// const HotelCardCarousel = () => {
//   const hotels = generateHotels(12); // Generate 12 hotels dynamically
//
//   return (
//     <div className="carousel-container">
//       {hotels.map((hotel) => (
//         <HotelCard key={hotel.id} hotel={hotel} /> // Render each hotel card
//       ))}
//     </div>
//   );
// };
const HotelCardCarousel = () => {
  const [hotels, setHotels] = useState([]);

  useEffect(() => {
    const fetchHotels = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/hotel/get_all_hotels");
        const hotelsFromBackend = response.data.map(hotel => ({
          ...hotel,
          images: ["src/assets/HotelCardCarousel/hotel1.jpg"], // Replace with actual image logic
          price: `€${Math.floor(150 + Math.random() * 100)}/night`, // Example price logic
          rating: (4 + Math.random()).toFixed(2), // Example rating logic
        }));
        setHotels(hotelsFromBackend);
      } catch (error) {
        console.error("Error fetching hotels:", error);
      }
    };

    fetchHotels();
  }, []);

  return (
      <div className="carousel-container">
        {hotels.map((hotel) => (
            <HotelCard key={hotel.id} hotel={hotel} />
        ))}
      </div>
  );
};


export default HotelCardCarousel;
