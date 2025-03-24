//package com.example.homies.demo.repository;
//
//import com.example.homies.demo.model.hotel.Cities;
//import com.example.homies.demo.model.hotel.Hotel;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//class HotelRepositoryTest {
//
//    @Autowired
//    private HotelRepository hotelRepository;
//
//    @Test
//    void testSearchAvailableHotels() {
//        // Seed test data
//        Hotel hotel = new Hotel();
//        hotel.setCity(Cities.MADRID);
//        hotelRepository.save(hotel);
//
//        // Test query
//        List<Hotel> hotels = hotelRepository.searchAvailableHotels(Cities.MADRID, null, null);
//        assertEquals(1, hotels.size());
//    }
//}