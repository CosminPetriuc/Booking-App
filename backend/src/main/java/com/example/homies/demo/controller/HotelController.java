package com.example.homies.demo.controller;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.dto.HotelDTO;
import com.example.homies.demo.model.dto.ReviewRequestDTO;
import com.example.homies.demo.model.dto.RoomRequestDTO;
import com.example.homies.demo.model.hotel.*;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.service.BookingService;
import com.example.homies.demo.service.HotelService;
import com.example.homies.demo.service.ReviewService;
import com.example.homies.demo.service.RoomsService;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotel")
@AllArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final RoomsService roomsService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final UserService userService;


    @PostMapping("/create_new_hotel")
    public Hotel createNewHotel(@RequestParam String name,
                                @RequestParam Cities city,
                                @RequestParam String description){
        return hotelService.createHotel(new ArrayList<>(),new ArrayList<>(),name,city,new ArrayList<>(),description);
    }
    @GetMapping("/create_hotel")
    public Hotel createHotel(@RequestParam List<Room> rooms,
                             @RequestParam List<Review> reviews,
                             @RequestParam String name,
                             @RequestParam Cities city,
                             @RequestParam List<Facilities> facilities,
                             @RequestParam String description){
        return hotelService.createHotel(rooms,reviews,name,city,facilities,description);
    }
    @GetMapping("/get_hotel_by_id")
    public Hotel getHotelById(@RequestParam Long hotelID){
        return hotelService.getHotelByID(hotelID);
    }
    @PostMapping("/add_review_to_hotel")
    public Hotel addReviewToHotel(@RequestParam Long hotelID,
                                  @RequestBody ReviewRequestDTO reviewDTO,
                                  @RequestParam Long userID){

        Review review = reviewService.saveReviewFromDTO(hotelID,userID, reviewDTO);
        return hotelService.addReviewToHotel(hotelID,review);

    }
    @PostMapping("/add_room_to_hotel")
    public Hotel addRoomToHotel(@RequestParam Long hotelID,
                                @RequestBody RoomRequestDTO roomRequestDTO){

        return hotelService.addRoomToHotel(hotelID,roomRequestDTO);
    }
    @GetMapping("get_reviews_by_hotel")
    public List<Review> getReviesByHotel(@RequestParam Long hotelID){
        return hotelService.getReviews(hotelID);
    }
    @GetMapping("/get_all_hotels")
    public List<Hotel> getAllHotels(){
        return hotelService.getAllHotels();
    }
//    @GetMapping("/available")
//    public ResponseEntity<?> searchAvailableHotels(
//            @RequestParam String city,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//        try {
//            // Fetch hotels in the specified city
//            List<Hotel> hotelList = hotelService.getAvailableHotels(Cities.valueOf(city));
//            List<Hotel> availableHotels = new ArrayList<>();
//
//            for (Hotel hotel : hotelList) {
//                boolean hotelAvailable = true;
//
//                List<Room> roomList = roomsService.getAllRoomsByHotelId(hotel);
//                for (Room room : roomList) {
//                    // Check bookings for this room if date range is provided
//                    if (startDate != null && endDate != null) {
//                        List<Booking> bookings = room.getReservations();
//                        for (Booking booking : bookings) {
//                            if (isDateOverlapping(booking.getStartDate(), booking.getEndDate(), startDate, endDate)) {
//                                hotelAvailable = false;
//                                break;
//                            }
//                        }
//                    }
//                    if (!hotelAvailable) {
//                        break;
//                    }
//                }
//                if (hotelAvailable) {
//                    availableHotels.add(hotel);
//                }
//            }
//
//            // Transform to DTOs here
//            List<HotelDTO> availableHotelsDTO = availableHotels.stream()
//                    .map(HotelDTO::new)
//                    .collect(Collectors.toList());
//
//            return new ResponseEntity<>(availableHotelsDTO, HttpStatus.OK);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>("Invalid city name provided: " + city, HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    private boolean isDateOverlapping(LocalDateTime bookingStart, LocalDateTime bookingEnd, LocalDateTime searchStart, LocalDateTime searchEnd) {
        return !(bookingEnd.isBefore(searchStart) || bookingStart.isAfter(searchEnd));
    }
}
