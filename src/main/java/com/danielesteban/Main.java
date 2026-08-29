package com.danielesteban;

import com.danielesteban.Services.BookingService;
import com.danielesteban.Services.PaymentService;
import com.danielesteban.Services.RoomService;
import com.danielesteban.dto.BookingDto;
import com.danielesteban.helpers.MailHelper;
import com.danielesteban.repositories.BookingRepository;
import com.danielesteban.repositories.PaymentRepository;
import com.danielesteban.repositories.RoomRepository;

import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        final var paymentService = new PaymentService(new PaymentRepository());
        final var roomService = new RoomService(new RoomRepository());
        final var bookingRepository = new BookingRepository();
        final var mailHelper = new MailHelper();
        final var bookingService = new BookingService(
                paymentService,
                roomService,
                bookingRepository,
                mailHelper
        );

        var randomId = UUID.randomUUID().toString();
        var bookingDto = new BookingDto(
                "1.1",
                LocalDate.of(2023, 06, 10),
                LocalDate.of(2023, 06, 20),
                2,
                true
        );
        var bookingResult = bookingService.booking(bookingDto);
        System.out.println(bookingResult);

        bookingService.unbook(bookingResult);

        var price = bookingService.calculatePrice(bookingDto);

        System.out.println(price);

        var priceMxn = bookingService.calculateInMxn(bookingDto);
        System.out.println(priceMxn);

        var roomsAvailable = bookingService.getAvailablePlaceCount();

        System.out.println(roomsAvailable);
    }
}