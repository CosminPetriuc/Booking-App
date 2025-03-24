import { useState } from "react";
import { useNavigate } from "react-router-dom";

import "./BookingSquare.css";

const BookingSquare = () => {
    const [currentImage, setCurrentImage] = useState("src/assets/image1.jpg"); // Default image in the card
    const [photoStripImages, setPhotoStripImages] = useState([
        "src/assets/image2.jpg",
        "src/assets/image3.jpg",
        "src/assets/image4.jpg",
    ]);

    const navigate = useNavigate();

    const handleImageClick = (image) => {
        // Check if the clicked image is not already the current image
        if (image !== currentImage) {
            // Add the current image to the end of the photo strip
            const updatedImages = [...photoStripImages, currentImage];

            // Update the photo strip images and set the clicked image as the current one
            setPhotoStripImages(updatedImages.filter((img) => img !== image));
            setCurrentImage(image);
        }
    };
    return (
        <div className="booking-square" onClick={() => navigate("/my-bookings")}>
            {/* Image Background of Square*/}
            <div className="image-card">
                {/* Image */}
                <img src={currentImage} alt="Selected Resort" className="image"/>
                {/* the whiteness that is over image */}
                <div className="white-overlay"></div>
                {/* Footer of square */}
                <div className="image-overlay">
                    <p className="image-text">My Bookings</p>
                </div>
                {/* Small photos container */}
                <div className="photo-strip">
                    <div className="photo-square">
                        <div
                            className="image-container"
                            onClick={() => handleImageClick("src/assets/image1.jpg")}
                        >
                            <img src="src/assets/image1.jpg" alt="Default Image"/>
                            <div className="squares-overlay"></div>
                        </div>
                    </div>

                    {photoStripImages.map((image, index) => (
                        <div className="photo-square" key={index}>
                            <div
                                className="image-container"
                                onClick={() => handleImageClick(image)}
                            >
                                <img src={image} alt={`Image ${index + 1}`}/>
                                <div className="squares-overlay"></div>
                            </div>
                        </div>
                    ))}

                    {/* Plus Sign Square */}
                    <div className="photo-square">
                        <div className="image-container plus-sign">
                            <img src="src/assets/plus-sign-image.jpg" alt="Plus Sign"/>
                            <div className="squares-overlay plus-sign">+</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default BookingSquare;