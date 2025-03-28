package com.example.homies.demo.repository;

import com.example.homies.demo.model.hotel.Hotel;
import com.example.homies.demo.model.hotel.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.hotel =:hotelId")
    Optional<List<Room>> findByHotelId(Hotel hotelId);

}
