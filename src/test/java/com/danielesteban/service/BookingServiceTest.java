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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
    @DisplayName("booking2 should work")
    void booking3() {
        final var roomId = UUID.randomUUID().toString();
        when(this.roomServiceMock.findAvailableRoom(any(BookingDto.class)))
                .thenReturn(DataDummy.default_rooms_list.get(0));
        when(this.bookingRepositoryMock.save(any(BookingDto.class)))
                .thenReturn(roomId);
        var result = this.bookingService.booking(DataDummy.default_booking_req_1);
        assert result.equals(roomId);
    }
}
