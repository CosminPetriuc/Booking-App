package com.example.homies.demo.DTOs_testing;

import com.example.homies.demo.model.dto.HotelDTO;
import com.example.homies.demo.model.hotel.Cities;
import com.example.homies.demo.model.hotel.Hotel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelDTOTesting {
    @Test
    void testHotelDTOMapping() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setCity(Cities.ROME);

        // Act
        HotelDTO hotelDTO = new HotelDTO(hotel);

        // Assert
        assertEquals("Test Hotel", hotelDTO.getName());
        assertEquals(Cities.valueOf("ROME"), hotelDTO.getCity());
    }

    @Test
    void testHotelDTOSettersAndGetters() {
        // Arrange
        HotelDTO hotelDTO = new HotelDTO();

        // Act
        hotelDTO.setName("Test Hotel");
        hotelDTO.setCity(Cities.valueOf("ROME"));

        // Assert
        assertEquals("Test Hotel", hotelDTO.getName());
        assertEquals(Cities.ROME, hotelDTO.getCity());
    }
}
