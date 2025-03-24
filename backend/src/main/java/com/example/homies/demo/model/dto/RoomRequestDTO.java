package com.example.homies.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {
    @NotBlank
    private Long roomID;

    @NotBlank
    private float roomSize;

    @NotBlank
    private int capacity;// number of beds in a room

    @NotBlank
    private float price;

    @NotBlank
    private int roomNumber;
}

