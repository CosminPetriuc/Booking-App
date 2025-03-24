import { useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "/node_modules/swiper/swiper-bundle.min.css"; // Import Swiper's styles
import "./AISquare.css";

// eslint-disable-next-line react/prop-types
const AISquare = ({ activeSquare, setActiveSquare }) => {
  const [activeSquare_1, setActiveSquare_1] = useState(false);
  const [userInput, setUserInput] = useState("");
  const [selectedCountry, setSelectedCountry] = useState("");
  const [activeCountryBtn, setActiveCountryBtn] = useState(false);
  const [placeholderVisible, setPlaceholderVisible] = useState(true);
  const countries = [
    "Vienna",
    "Rome",
    "Paris",
    "Bucharest",
    "Amsterdam",
    "Berlin",
  ];
  const [chatLog, setChatLog] = useState([
    { type: "outgoing", text: "Hi there, Y/N!" },
    { type: "outgoing", text: "Where would you like to go?" },
  ]);

  const handleCountryBtnClick = (country) => {
    setSelectedCountry(country);
    setActiveCountryBtn(true);
    setChatLog((prevLog) => [
      ...prevLog,
      { type: "incoming", text: `I'd like to visit ${country}!` },
    ]);
  };

  const handleActiveAISquare = () => {
    if (activeSquare_1 === false) {
      setActiveSquare_1(true);
    }
  };

  const sendMessage = () => {
    if (userInput.trim() !== "") {
      setChatLog((prevLog) => [
        ...prevLog,
        { type: "incoming", text: userInput.trim() },
      ]);
      setUserInput("");
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  return (
    <div
      className={`AI-square ${
        activeSquare || activeSquare_1 === true ? "active" : ""
      }`}
      onClick={() => {
        handleActiveAISquare();
        if (!activeSquare) {
          setActiveSquare(true);
        }
      }}
    >
      <div className="AI-header">
        <div className="AI-icon">
          <i className="bi bi-stars"></i>
        </div>
        <span className="assistant-title">AI Assistant</span>

        {(activeSquare || activeSquare_1) && (
          <div
            className="AI-close-icon"
            onClick={(e) => {
              e.stopPropagation(); // Prevent triggering the parent onClick

              // Reset both states
              if (activeSquare) {
                setActiveSquare(false);
              }
              if (activeSquare_1) {
                setActiveSquare_1(false);
              }

              setActiveCountryBtn(false);
              setUserInput(""); // Clear the random user input

              setChatLog([
                { type: "outgoing", text: "Hi there, Y/N!" },
                { type: "outgoing", text: "Where would you like to go?" },
              ]);
            }}
          >
            <i className="bi bi-x-circle-fill"></i>
          </div>
        )}
      </div>
      <div className="AI-text-display-container">
        {chatLog.map((chat, idx) => (
          <div key={idx} className={`${chat.type} chat`}>
            {chat.type === "outgoing" && (
              <span className="bi bi-robot chat-icon"></span>
            )}
            <div className={`msg-${chat.type} msg`}>
              <div className="text">
                <p>{chat.text}</p>
              </div>
            </div>
            {chat.type === "incoming" && (
              <span className="bi bi-person-circle chat-icon"></span>
            )}
          </div>
        ))}
      </div>
      <div className="AI-cities-container">
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            flexDirection: "row",
            width: "100%",
            height: "100%",
            paddingLeft: "15px",
            paddingRight: "15px",
            backgroundColor: "transparent",
          }}
        >
          <Swiper
            slidesPerView={3}
            spaceBetween={30}
            breakpoints={{
              1024: {
                slidesPerView: 3,
                spaceBetween: 30,
              },
              600: {
                slidesPerView: 2,
                spaceBetween: 20,
              },
              480: {
                slidesPerView: 1,
                spaceBetween: 10,
              },
            }}
          >
            {countries.map((country, idx) => (
              <SwiperSlide key={idx}>
                <button
                  className={`btn btn-dark custom-btn ${
                    activeCountryBtn === true && selectedCountry === country
                      ? "active"
                      : ""
                  }`}
                  onClick={() => handleCountryBtnClick(country)}
                >
                  <div className="country-name">{country}</div>
                </button>
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
      </div>
      <div className="AI-text-input-container">
        <div className="AI-text-box">
          <i className="bi bi-upload upload-btn"></i>
          <input
            type="text"
            className="form-control"
            id="formGroupExampleInput"
            disabled={false} // user should not be able to type in the input field
            placeholder={placeholderVisible ? "Enter your description" : ""}
            value={userInput} // Bind to userInput state
            onChange={(e) => setUserInput(e.target.value)} // Update userInput on input
            onFocus={() => setPlaceholderVisible(false)}
            onBlur={() => setPlaceholderVisible(true)}
            onKeyDown={handleKeyPress}
          />
          <i
            className={`bi bi-send ${userInput === "" ? "disabled" : "active"}`}
          ></i>
        </div>
      </div>
    </div>
  );
};

export default AISquare;
