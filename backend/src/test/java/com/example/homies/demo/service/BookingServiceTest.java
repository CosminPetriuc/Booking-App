package com.example.homies.demo.service;

import com.example.homies.demo.model.booking.Booking;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.BookingRepository;
import com.example.homies.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    private User mockUser;
    private Booking mockBooking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock user
        mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setEmail("mockemail@mockings.com");
        mockUser.setFirstName("mock");
        mockUser.setLastName("user");
        mockUser.setRoles(new ArrayList<>());
        mockUser.setBookings(new ArrayList<>());

        // Initialize mock booking
        mockBooking = new Booking();
        mockBooking.setBookingId(1L);
        mockBooking.setUser(mockUser);
        mockBooking.setStartDate(LocalDateTime.now().plusDays(1));
        mockBooking.setEndDate(LocalDateTime.now().plusDays(2));
        mockBooking.setRooms(new ArrayList<>());
        mockBooking.setTotalPrice(100);
    }

    @Test
    void testGetMyUpcomingBookings() {
        Long userId = mockUser.getUserId();
        LocalDateTime now = LocalDateTime.now();

        // Prepare mock data


        // Mock repository behavior
        //when(bookingRepository.findUpcomingBookingsByUser(userId, now)).thenReturn(mockBookings);

        // Call service method
        //List<Booking> bookings = bookingService.getMyUpcomingBookings(userId);

        // Assertions
        assertEquals(1, bookingService.getMyUpcomingBookings(1L), "The number of bookings should be 1");
        assertEquals(mockBooking.getBookingId(), bookingService.getMyUpcomingBookings(1L).get(0), "The booking ID should match the mock");

        // Verify interactions
        verify(bookingRepository, times(1)).findUpcomingBookingsByUser(userId, now);
    }

    @Test
    void testGetBookingByDate_Success() throws Exception {
        LocalDateTime startDate = mockBooking.getStartDate();
        LocalDateTime endDate = mockBooking.getEndDate();

        // Mock repository behavior
        when(bookingRepository.findByStartAndEndDate(startDate, endDate)).thenReturn(Optional.of(mockBooking));

        // Call service method
        Booking booking = bookingService.getBookingByDate(startDate, endDate);

        // Assertions
        assertNotNull(booking, "Booking should not be null");
        assertEquals(mockBooking.getBookingId(), booking.getBookingId(), "Booking ID should match the mock");

        // Verify interactions
        verify(bookingRepository, times(1)).findByStartAndEndDate(startDate, endDate);
    }

    @Test
    void testGetBookingByDate_NotFound() {
        LocalDateTime startDate = mockBooking.getStartDate();
        LocalDateTime endDate = mockBooking.getEndDate();

        // Mock repository behavior
        when(bookingRepository.findByStartAndEndDate(startDate, endDate)).thenReturn(Optional.empty());

        // Call service method and capture exception
        Exception exception = assertThrows(Exception.class, () -> bookingService.getBookingByDate(startDate, endDate));

        // Assertions
        assertEquals("There are no bookings in that date", exception.getMessage());

        // Verify interactions
        verify(bookingRepository, times(1)).findByStartAndEndDate(startDate, endDate);
    }

//    @Test
//    void testCreateBooking() {
//        LocalDateTime startDate = mockBooking.getStartDate();
//        LocalDateTime endDate = mockBooking.getEndDate();
//        Long userId = mockUser.getUserId();
//        List<Room> rooms = new ArrayList<>();
//
//        // Mock repository behavior
//        when(userRepository.getReferenceById(userId)).thenReturn(mockUser);
//        when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);
//
//        // Call service method
//        Booking createdBooking = bookingService.createBooking(startDate, endDate, userId, rooms,1,0,0);
//
//        // Assertions
//        assertNotNull(createdBooking, "Created booking should not be null");
//        assertEquals(mockBooking.getBookingId(), createdBooking.getBookingId(), "Booking ID should match the mock");
//
//        // Verify interactions
//        verify(userRepository, times(1)).getReferenceById(userId);
//        verify(bookingRepository, times(1)).save(any(Booking.class));
//    }

    @Test
    void testDeleteBooking_Success() throws Exception {
        Long bookingId = mockBooking.getBookingId();

        // Mock repository behavior
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        // Call service method
        boolean result = bookingService.deleteBooking(bookingId);

        // Assertions
        assertTrue(result, "Delete should return true");

        // Verify interactions
        verify(bookingRepository, times(1)).delete(mockBooking);
    }

    @Test
    void testDeleteBooking_NotFound() {
        Long bookingId = 2L; // A non-existent booking ID

        // Mock repository behavior
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // Call service method
        boolean result = bookingService.deleteBooking(bookingId);

        // Assertions
        assertFalse(result, "Delete should return false");

        // Verify interactions
        verify(bookingRepository, never()).delete(any(Booking.class));
    }

    @Test
    void testGetAllBookings() {
        // Prepare mock data
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(mockBooking);

        // Mock repository behavior
        when(bookingRepository.findAll()).thenReturn(mockBookings);

        // Call service method
        List<Booking> bookings = bookingService.getAllBookings();

        // Assertions
        assertEquals(1, bookings.size(), "The number of bookings should be 1");
        assertEquals(mockBooking.getBookingId(), bookings.get(0).getBookingId(), "Booking ID should match the mock");

        // Verify interactions
        verify(bookingRepository, times(1)).findAll();
    }
}
