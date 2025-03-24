import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "leaflet/dist/leaflet.css";
import Home from "./components/Home/Home";
import Bookings from "./components/Bookings/Bookings";
import HotelPage from "./components/HotelPage/HotelPage";
import MyBookings from "./components/MyBookings/MyBookings";
import Map from "./components/Map/Map";
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Profile from "./components/Profile/Profile";
import "./App.css";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<Home />} /> {/* Default to Home */}
        <Route path="/bookings" element={<Bookings />} />
        <Route path="/my-bookings" element={<MyBookings />} />
        <Route path="/map" element={<Map />} />
        <Route path="/register" element={<Register />} />
        <Route path="/settings" element={<Profile />} />
        <Route path="/bookings/hotel/:hotelId" element={<HotelPage />} />
        <Route path="*" element={<p>Page not found</p>} />{" "}
        {/* Catch-all route */}
      </Routes>
    </Router>
  );
};

export default App;
