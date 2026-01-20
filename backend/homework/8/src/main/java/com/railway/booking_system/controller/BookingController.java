package com.railway.booking_system.controller;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.RetryDlqQueue;
import com.railway.booking_system.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookingController {

    private final BookingService bookingService; // Exercise 1: pub-sub publish
    private final RetryDlqQueue<TicketBookedDto> bookingMainQueue; // Exercise 2: retry + DLQ

    public BookingController(BookingService bookingService,
            RetryDlqQueue<TicketBookedDto> bookingMainQueue) {
        this.bookingService = bookingService;
        this.bookingMainQueue = bookingMainQueue;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookTicket(@RequestBody TicketBookedDto req) {
        // Ensure bookingId exists
        if (req.bookingId == null || req.bookingId.isBlank()) {
            req.bookingId = "BKG-" + UUID.randomUUID();
        }

        // default age if client doesn't send it (so it won't crash on missing field)
        if (req.age == 0)
            req.age = 25;

        // Exercise 1: publish TicketBooked event to topic/exchange (Inventory +
        // Notification consume)
        bookingService.createBooking(req);

        // Exercise 2: push booking message into main queue for processing with
        // retry+DLQ handling
        bookingMainQueue.push(req);

        // Immediately return (non-blocking)
        return ResponseEntity.ok(new BookingResponse("Booking in Progress", req.bookingId));
    }

    public static class BookingResponse {
        public final String status;
        public final String bookingId;

        public BookingResponse(String status, String bookingId) {
            this.status = status;
            this.bookingId = bookingId;
        }
    }
}
