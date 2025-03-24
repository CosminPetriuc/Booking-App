package com.example.homies.demo.repository;

import com.example.homies.demo.model.hotel.Cities;
import com.example.homies.demo.model.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * Search for available hotels in a specific city and optionally within a date range.
//     * @param city      The city to search hotels in.
//     * @param startDate (Optional) The start date of the availability.
//     * @param endDate   (Optional) The end date of the availability.
     * @return List of hotels in the city, optionally filtered by availability.
     */
//    @Query("SELECT h FROM Hotel h " +
//            "JOIN h.rooms r " +
//            "WHERE h.city = :city AND " +
//            "(:startDate IS NULL OR :endDate IS NULL OR NOT EXISTS (" +
//            "SELECT b FROM Booking b " +
//            "WHERE b.rooms = r AND " +
//            "(b.startDate <= :endDate AND b.endDate >= :startDate)))")

    @Query("SELECT h FROM Hotel h WHERE h.city= :city")
    Optional<List<Hotel>> searchAvailableHotels(Cities city);
}
