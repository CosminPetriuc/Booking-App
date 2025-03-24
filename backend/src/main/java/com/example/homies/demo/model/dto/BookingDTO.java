package com.example.homies.demo.model.dto;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.booking.Payment;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Long bookingId;  // Added for identifying the booking to update
    private double totalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int adultsNumber;
    private int kidsNumber;
    private int petsNumber;
    public BookingDTO(Booking booking){
        this.setBookingId(booking.getBookingId());
        this.setTotalPrice(booking.getTotalPrice());
        this.setStartDate(booking.getStartDate());
        this.setEndDate(booking.getEndDate());
        this.setAdultsNumber(booking.getAdultsNumber());
        this.setKidsNumber(booking.getKids_number());
        this.setPetsNumber(booking.getPetsNumber());
    }
}

