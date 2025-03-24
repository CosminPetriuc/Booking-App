package com.example.homies.demo.model.booking;

import com.example.homies.demo.model.dto.BookingDTO;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Entity(name = "Booking")
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "roomId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @Column(name = "adults_number")
    private int adultsNumber;

    @Column(name = "kids_number")
    private int kids_number;

    @Column(name = "pets_number")
    private int petsNumber;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Booking(BookingDTO bookingDTO, User user){
        this.setUser(user);
        this.setPayments(new ArrayList<>());
        this.setPetsNumber(bookingDTO.getPetsNumber());
        this.setAdultsNumber(bookingDTO.getAdultsNumber());
        this.setRooms(new ArrayList<>());
        this.setTotalPrice(bookingDTO.getTotalPrice());
        this.setStartDate(bookingDTO.getStartDate());
        this.setEndDate(bookingDTO.getEndDate());
        this.setKids_number(bookingDTO.getKidsNumber());
    }

}
