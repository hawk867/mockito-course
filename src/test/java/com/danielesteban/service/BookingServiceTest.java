package com.danielesteban.service;

import com.danielesteban.Services.BookingService;
import com.danielesteban.Services.PaymentService;
import com.danielesteban.Services.RoomService;
import com.danielesteban.dto.BookingDto;
import com.danielesteban.helpers.MailHelper;
import com.danielesteban.repositories.BookingRepository;
import com.danielesteban.utils.DataDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock private PaymentService paymentServiceMock;
    @Mock private RoomService roomServiceMock;
    @Mock private BookingRepository bookingRepositoryMock;
    @Mock private MailHelper mailHelperMock;

    @Test
    @DisplayName("Get available place count should works")
    void getAvailablePlaceCount() {
        when(this.roomServiceMock.findAllAvailableRooms())
                .thenReturn(DataDummy.default_rooms_list);
        var expected = 15;
        var result = this.bookingService.getAvailablePlaceCount();
        assert result == expected;
    }

    @Test
    @DisplayName("booking1 should work")
    void booking1() {
        final var roomId = UUID.randomUUID().toString();
        when(this.roomServiceMock.findAvailableRoom(DataDummy.default_booking_req_1))
                .thenReturn(DataDummy.default_rooms_list.get(0));
        when(this.bookingRepositoryMock.save(DataDummy.default_booking_req_1))
                .thenReturn(roomId);
        var result = this.bookingService.booking(DataDummy.default_booking_req_1);
        assert result.equals(roomId);
    }

    @Test
    @DisplayName("booking2 should work")
    void booking2() {
        final var roomId = UUID.randomUUID().toString();
        doReturn(DataDummy.default_rooms_list.get(1))
                .when(this.roomServiceMock).findAvailableRoom(DataDummy.default_booking_req_2);
        doReturn(roomId)
                .when(this.bookingRepositoryMock).save(DataDummy.default_booking_req_2);
        var result = this.bookingService.booking(DataDummy.default_booking_req_2);
        assert result.equals(roomId);
    }

    @Test
    @DisplayName("booking3 should work")
    void booking3() {
        //arrange
        final var roomId = UUID.randomUUID().toString();

        doNothing().when(this.roomServiceMock).bookRoom(anyString());
        when(this.roomServiceMock.findAvailableRoom(any(BookingDto.class)))
                .thenReturn(DataDummy.default_rooms_list.get(0));
        when(this.bookingRepositoryMock.save(any(BookingDto.class)))
                .thenReturn(roomId);

        //act
        var result = this.bookingService.booking(DataDummy.default_booking_req_1);

        //assert
        assertEquals(result, roomId);

        verify(this.roomServiceMock).findAvailableRoom(DataDummy.default_booking_req_1);
        verify(this.bookingRepositoryMock).save(DataDummy.default_booking_req_1);
        verify(this.roomServiceMock, times(1)).bookRoom(anyString());
    }

    @Test
    @DisplayName("booking unhappy path should fail")
    void bookingUnhappyPath() {
        //arrange
        when(this.roomServiceMock.findAvailableRoom(any(BookingDto.class)))
                .thenReturn(DataDummy.default_rooms_list.get(0));
        doThrow(new IllegalArgumentException("Max 3 guest"))
                .when(this.paymentServiceMock).pay(eq(DataDummy.default_booking_req_3), eq(100.0));

        //act
        Executable result = () -> this.bookingService.booking(DataDummy.default_booking_req_3);

        //assert
        assertThrows(IllegalArgumentException.class, result);

    }
}
