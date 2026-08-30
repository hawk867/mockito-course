package com.danielesteban.service;

import com.danielesteban.Services.RoomService;
import com.danielesteban.repositories.RoomRepository;
import com.danielesteban.utils.DataDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;
    @Mock
    private RoomRepository roomRepositoryMock;

//    @BeforeEach
//    void init() {
//        this.roomRepositoryMock = mock(RoomRepository.class);
//        this.roomService = new RoomService(roomRepositoryMock);
//    }

    @Test
    @DisplayName("Should get all rooms available in room repository")
    void findAllAvailableRooms() {
        when(roomRepositoryMock.findAll())
                .thenReturn(DataDummy.default_rooms);
        var expected = 3;
        var result = this.roomService.findAllAvailableRooms();
        assert result.size() == expected;
    }
}
