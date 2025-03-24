// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";

// import MapComponent from "./MapComponent/MapComponent.jsx";
// import GuestDetails from "./GuestDetails/GuestDetails.jsx";
// import "./Map.css";

// import MenuBar from "../Home/MenuBar/MenuBar.jsx";

// const Map = () => {
//   const [selectedCity, setSelectedCity] = useState("");

//   const handleCitySelect = (city) => {
//     setSelectedCity(city);
//     console.log(`Selected city: ${city}`);
//   };

//   return (
//     <div className="map-container">
//       <MenuBar initialActiveIndex={2} /> {/* Highlight Map icon */}
//       <div className="content-container-map">
//         <MapComponent onCitySelect={handleCitySelect} />
//         <GuestDetails
//           selectedCity={selectedCity}
//           onCityChange={setSelectedCity}
//         />
//       </div>
//     </div>
//   );
// };

// export default Map;

import React, { useState } from "react";
import MapComponent from "./MapComponent/MapComponent.jsx";
import GuestDetails from "./GuestDetails/GuestDetails.jsx";
import "./Map.css";

import MenuBar from "../Home/MenuBar/MenuBar.jsx";

const Map = () => {
  const [selectedCity, setSelectedCity] = useState("");

  const handleCitySelect = (city) => {
    setSelectedCity(city);
    console.log(`Selected city: ${city}`);
  };

  return (
    <div className="map-container">
      <MenuBar initialActiveIndex={2} /> {/* Highlight Map icon */}
      <div className="content-container-map">
        <MapComponent onCitySelect={handleCitySelect} />
        <GuestDetails
          selectedCity={selectedCity}
          onCityChange={setSelectedCity}
        />
      </div>
    </div>
  );
};

export default Map;
