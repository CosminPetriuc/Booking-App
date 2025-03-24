package com.example.homies.demo.controller;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.dto.BookingDTO;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @GetMapping("/get_upcoming_bookings_by_userID")
    public List<Booking> getUserUpcomingBookings(@RequestParam Long userID){
        return bookingService.getMyUpcomingBookings(userID);
    }
    @PostMapping("/create_booking")
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO,
                                 @RequestParam Long userID,
                                 @RequestParam List<Long> roomIds){
        return new BookingDTO(bookingService.createBooking(bookingDTO,userID,roomIds));
    }
    @PutMapping("/update_booking")
    public boolean updateBooking(@RequestBody BookingDTO bookingDTO,
                                 @RequestParam Long userID,
                                 @RequestParam List<Long> roomIDs) {
        return bookingService.updateBooking(bookingDTO,userID,roomIDs);

    }
    @DeleteMapping ("/delete_booking")
    public boolean deleteBooking(@RequestParam Long bookingID)
    {
        return bookingService.deleteBooking(bookingID);
    }
    @GetMapping("/get_all_bookings")
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }
}
