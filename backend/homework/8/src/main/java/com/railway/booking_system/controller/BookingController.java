package com.railway.booking_system.controller;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<?> bookTicket(@RequestBody TicketBookedDto req) {
        // ensure bookingId set
        if (req.bookingId == null || req.bookingId.isEmpty()) {
            req.bookingId = "BKG-" + UUID.randomUUID().toString();
        }
        // non-blocking publish
        String bookingId = bookingService.createBooking(req);
        return ResponseEntity.ok().body(new BookingResponse("Booking in Progress", bookingId));
    }

    static class BookingResponse {
        public final String status;
        public final String bookingId;

        public BookingResponse(String status, String bookingId) {
            this.status = status;
            this.bookingId = bookingId;
        }
    }
}
