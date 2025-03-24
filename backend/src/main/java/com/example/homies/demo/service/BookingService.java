package com.example.homies.demo.service;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.dto.BookingDTO;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.BookingRepository;
import com.example.homies.demo.repository.UserRepository;
import com.example.homies.demo.service.UserServiceLayer.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RoomsService roomsService;
    public List<Booking> getMyUpcomingBookings(Long userId){
        LocalDateTime currentDate = LocalDateTime.now();
        return bookingRepository.findUpcomingBookingsByUser(userId,currentDate);
    }

    public Booking getBookingByDate(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        return bookingRepository.findByStartAndEndDate(startDate, endDate)
                .orElseThrow(()-> new Exception("There are no bookings in that date"));
    }

    public Booking getBookingById(Long bookingID) throws Exception {
        return bookingRepository.findById(bookingID)
                .orElseThrow(()->new Exception("NO BOOKINGS. There are no bookings matching this id"));
    }
    public Booking createBooking(BookingDTO bookingDTO, Long userID,List<Long> roomIds){

        Booking newBooking = new Booking(bookingDTO,userService.findUserById(userID));

        List<Room> rooms = new ArrayList<>();

        for(Long roomID : roomIds){
            rooms.add(roomsService.getRoomByID(roomID));
        }
        newBooking.setRooms(rooms);

        return bookingRepository.save(newBooking);
    }
    public boolean updateBooking(BookingDTO bookingDTO, Long userID, List<Long> roomIDs) {
        // Fetch the old booking by ID
        Booking oldBooking = bookingRepository.findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + bookingDTO.getBookingId()));

        // Ensure the booking belongs to the correct user
        if (!oldBooking.getUser().getUserId().equals(userID)) {
            throw new SecurityException("Unauthorized to update this booking.");
        }

        // Update fields in the existing booking
        oldBooking.setTotalPrice(bookingDTO.getTotalPrice());
        oldBooking.setStartDate(bookingDTO.getStartDate());
        oldBooking.setEndDate(bookingDTO.getEndDate());
        oldBooking.setAdultsNumber(bookingDTO.getAdultsNumber());
        oldBooking.setKids_number(bookingDTO.getKidsNumber());
        oldBooking.setPetsNumber(bookingDTO.getPetsNumber());

        // Update rooms in the booking
        List<Room> updatedRooms = new ArrayList<>();
        for(Long roomid: roomIDs){
            updatedRooms.add(roomsService.getRoomByID(roomid));
        }
        oldBooking.getRooms().clear();
        oldBooking.getRooms().addAll(updatedRooms);

        // Save the updated booking
        bookingRepository.save(oldBooking);

        // Return success status
        return true;
    }

    public boolean deleteBooking(Long bookingID)
    {
        Booking booking = new Booking();
        boolean deletedBooking = true;

        try{
            booking = getBookingById(bookingID); // checking if there exist a booking with that id in the first place
        }catch (Exception e){
            deletedBooking = false;
        }

        if(deletedBooking){
            try{
                bookingRepository.delete(booking);
            }catch (Exception e){
                System.out.println("ERROR: Can't delete booking " + bookingID + " check line 84 in service/BookingService.java" );
            }
        }
        return deletedBooking;
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }


}
