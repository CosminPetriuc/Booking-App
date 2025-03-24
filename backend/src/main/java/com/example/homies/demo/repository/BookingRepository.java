package com.example.homies.demo.repository;

import com.example.homies.demo.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.startDate > :currentDate")
    List<Booking> findUpcomingBookingsByUser(
            @Param("userId") Long userId,
            @Param("currentDate") LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.startDate= :startDate AND b.endDate= :endDate")
    Optional<Booking> findByStartAndEndDate(LocalDateTime startDate, LocalDateTime endDate);
}
