package com.example.homies.demo.service;

import com.example.homies.demo.model.dto.RoomRequestDTO;
import com.example.homies.demo.model.hotel.*;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.HotelRepository;
import com.example.homies.demo.repository.UserRepository;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomsService roomsService;
    private final UserService userService;

    public List<Hotel> getAvailableHotels(Cities city) throws Exception {
        return hotelRepository.searchAvailableHotels(city)
                .orElseThrow(()->new Exception("There are bo available hotels"));
    }
    public Hotel createHotel(List<Room> rooms,
                             List<Review> reviews,
                             String name,
                             Cities city,
                             List<Facilities> facilities,
                             String description){
        Hotel hotel = new Hotel();
        hotel.setRooms(rooms);
        hotel.setReviews(reviews);
        hotel.setName(name);
        hotel.setCity(city);
        hotel.setFacilities(facilities);
        hotel.setDescription(description);

        return hotelRepository.save(hotel);
    }
    public Hotel updateHotel(Long hotelID, Hotel updatedHotel) {
        Optional<Hotel> optionalOldHotel = hotelRepository.findById(hotelID);

        if (optionalOldHotel.isPresent()) {
            Hotel oldHotel = optionalOldHotel.get();

            // Update simple fields
            oldHotel.setName(updatedHotel.getName());
            oldHotel.setCity(updatedHotel.getCity());
            oldHotel.setDescription(updatedHotel.getDescription());

            // Update facilities list
            oldHotel.setFacilities(updatedHotel.getFacilities());

            // Update rooms (cascade handles orphan removal)
            oldHotel.getRooms().clear();
            oldHotel.getRooms().addAll(updatedHotel.getRooms());

            // Update reviews (cascade handles orphan removal)
            oldHotel.getReviews().clear();
            oldHotel.getReviews().addAll(updatedHotel.getReviews());

            // Save updated hotel
            Hotel savedHotel = hotelRepository.save(oldHotel);

            if (savedHotel.equals(oldHotel)) {
                System.out.println("! ATTENTION !: No changes were made to the hotel");
            }
            return savedHotel;
        } else {
            throw new RuntimeException("Hotel with ID " + hotelID + " not found");
        }
    }

    public Hotel addRoomToHotel(Long hotelID, RoomRequestDTO roomRequestDTO) {
        // Retrieve the Hotel entity
        Hotel hotel = hotelRepository.findById(hotelID)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with ID: " + hotelID));

        // Create a new Room entity
        Room newRoom = new Room(roomRequestDTO);

        // Associate the Room with the Hotel
        newRoom.setHotel(hotel);

        // Save the Room entity
        roomsService.saveRoom(newRoom);

        // No need to update the Hotel explicitly; the Room is now associated
        return hotel;
    }

    public Hotel addReviewToHotel(Long hotelID, Review review){

        Hotel hotel = hotelRepository.getReferenceById(hotelID);

        review.setHotel(hotel);

        List<Review> reviews = hotel.getReviews();
        reviews.add(review);

        hotel.setReviews(reviews);

        return updateHotel(hotelID,hotel);
    }
    public List<Review> getReviews(Long HotelID){
        return hotelRepository.getReferenceById(HotelID).getReviews();
    }
    public Hotel getHotelByID(Long hotelID){
        return hotelRepository.getReferenceById(hotelID);
    }
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }
}
