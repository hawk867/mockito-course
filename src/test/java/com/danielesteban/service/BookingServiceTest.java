package com.danielesteban.service;

import com.danielesteban.Services.BookingService;
import com.danielesteban.Services.PaymentService;
import com.danielesteban.Services.RoomService;
import com.danielesteban.helpers.MailHelper;
import com.danielesteban.repositories.BookingRepository;
import com.danielesteban.utils.DataDummy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
