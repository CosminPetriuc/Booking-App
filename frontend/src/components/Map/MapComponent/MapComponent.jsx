import React from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet"; // Import necessary Leaflet components
import L from "leaflet"; // Import Leaflet for additional configuration
import "./MapComponent.css";

const MapComponent = ({ onCitySelect }) => {
  // Cities array with coordinates
  const cities = [
    { name: "London", coordinates: [51.5074, -0.1278] },
    { name: "Paris", coordinates: [48.8566, 2.3522] },
    { name: "Berlin", coordinates: [52.52, 13.405] },
    { name: "Rome", coordinates: [41.9028, 12.4964] },
    { name: "Madrid", coordinates: [40.4168, -3.7038] },
    { name: "Vienna", coordinates: [48.2082, 16.3738] },
    { name: "Amsterdam", coordinates: [52.3676, 4.9041] },
    { name: "Lisbon", coordinates: [38.7169, -9.1395] },
    { name: "Dublin", coordinates: [53.3498, -6.2603] },
    { name: "Prague", coordinates: [50.0755, 14.4378] },
    { name: "Budapest", coordinates: [47.4979, 19.0402] },
    { name: "Athens", coordinates: [37.9838, 23.7275] },
  ];

  return (
    <div
      className="map-component"
      style={{
        height: "90vh", // Ensuring the height of the map
        width: "100%",
        position: "relative",
        borderRadius: "10px",
        boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
      }}
    >
      {/* Leaflet MapContainer */}
      <MapContainer
        center={[51.5074, -0.1278]}
        zoom={4}
        style={{ width: "100%", height: "100%" }}
      >
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" // OpenStreetMap URL
        />
        {/* Place markers for each city */}
        {cities.map((city, index) => (
          <Marker
            key={index}
            position={city.coordinates}
            eventHandlers={{
              click: () => {
                // Call onCitySelect when a city marker is clicked
                onCitySelect(city.name);
              },
            }}
          >
            <Popup>{city.name}</Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
};

export default MapComponent;
