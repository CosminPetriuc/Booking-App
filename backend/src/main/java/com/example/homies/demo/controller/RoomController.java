package com.example.homies.demo.controller;

import com.example.homies.demo.model.dto.RoomRequestDTO;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.service.RoomsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomController {

    private final RoomsService roomsService;

    @GetMapping("/get_all_rooms")
    public List<Room> getAllRooms(){
        return roomsService.getAllRooms();
    }
    @PostMapping("/create_room")
    public Room createRoom(@RequestBody RoomRequestDTO roomRequestDTO,
                           @RequestParam Long hotelID){
        return  roomsService.createRoom(roomRequestDTO,hotelID);
    }

}
