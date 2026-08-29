package com.danielesteban.Services;

import com.danielesteban.dto.BookingDto;
import com.danielesteban.dto.RoomDto;
import com.danielesteban.exceptions.AirdndException;
import com.danielesteban.repositories.RoomRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public RoomDto findAvailableRoom(BookingDto booking) {
        return this.roomRepository.findAll().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .filter(room -> Objects.equals(room.getCapacity(), booking.getTotalGuest()))
                .findFirst()
                .orElseThrow(() -> new AirdndException("Cant find room"));
    }

    public List<RoomDto> findAllAvailableRooms() {
        return this.roomRepository.findAll().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Integer getTotalRooms() {
        return this.roomRepository.findAll().size();
    }

    public void bookRoom(String roomId) {
        RoomDto room = this.roomRepository.findAll()
                .entrySet().stream()
                .filter(entryRoom ->  entryRoom.getKey().getId().equals(roomId) && entryRoom.getValue())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new AirdndException("cant get room"));

        this.roomRepository.save(room, true);
    }

    public void unbookRoom(String roomId) {
        RoomDto room = this.roomRepository.findAll()
                .entrySet().stream()
                .filter(entryRoom ->  entryRoom.getKey().getId().equals(roomId))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new AirdndException("cant get room"));

        this.roomRepository.save(room, false);
    }
}
