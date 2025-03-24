package com.example.homies.demo.model.hotel;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.dto.RoomRequestDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Entity(name = "Room")
@Table(name = "rooms")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roomId")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;  // Each room belongs to a single hotel

    @Column(name = "roomSize")
    private float roomSize;

    @Column(name = "capacity")
    private int capacity;//number of beds in a room

    @Column(name = "price")
    private float price;

    @Column(name = "room_number")
    private float roomNumber;

    public Room(RoomRequestDTO roomRequestDTO){
        this.setRoomSize(roomRequestDTO.getRoomSize());
        this.setRoomId(roomRequestDTO.getRoomID());
        this.setCapacity(roomRequestDTO.getCapacity());
        this.setHotel(new Hotel());
        this.setPrice(roomRequestDTO.getPrice());
        this.setRoomNumber(roomRequestDTO.getRoomNumber());
    }

}
