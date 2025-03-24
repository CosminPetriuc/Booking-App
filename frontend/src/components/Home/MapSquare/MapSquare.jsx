import { useNavigate } from "react-router-dom";
import "./MapSquare.css";

const MapSquare = () => {
    const mapImage = "src/assets/map.png";
    const navigate = useNavigate();

    return (
        <div className="map-square">
            <div className="map-img-container" onClick={() => navigate("/map")}>
                <img src={mapImage} className="map-img" alt="Map"></img>

            </div>
        </div>
    );
}
export default MapSquare;