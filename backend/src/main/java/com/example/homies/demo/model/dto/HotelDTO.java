package com.example.homies.demo.model.dto;

import com.example.homies.demo.model.hotel.Cities;
import com.example.homies.demo.model.hotel.Hotel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelDTO {
    private String name;
    private Cities city;

    // Constructor to map from Hotel entity
    public HotelDTO(Hotel hotel) {
        this.name = hotel.getName();
        this.city = hotel.getCity();
    }


}
