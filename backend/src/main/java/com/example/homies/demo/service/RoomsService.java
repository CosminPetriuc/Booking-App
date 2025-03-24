package com.example.homies.demo.service;

import com.example.homies.demo.model.dto.RoomRequestDTO;
import com.example.homies.demo.model.hotel.Hotel;
import com.example.homies.demo.model.hotel.Room;
import com.example.homies.demo.repository.HotelRepository;
import com.example.homies.demo.repository.RoomsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomsService {

    private final RoomsRepository roomsRepository;
    private final HotelRepository hotelRepository;

    public List<Room> getAllRoomsByHotelId(Hotel hotelId) throws Exception {
        return roomsRepository.findByHotelId(hotelId)
                .orElseThrow(() -> new Exception("There are no rooms"));
    }
    public Room createRoom(RoomRequestDTO roomRequestDTO,
                           Long hotelID){
        Room room = new Room(roomRequestDTO);
        room.setHotel(hotelRepository.getReferenceById(hotelID));
        return roomsRepository.save(room);
    }
    public Room saveRoom(Room room){
        return roomsRepository.save(room);
    }
    public Room getRoomByID(Long roomID){
        return roomsRepository.getReferenceById(roomID);
    }
    public List<Room> getAllRooms(){
        return roomsRepository.findAll();
    }
}
