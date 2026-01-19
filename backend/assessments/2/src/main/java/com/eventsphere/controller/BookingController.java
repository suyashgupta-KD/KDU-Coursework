package com.eventsphere.controller;

import com.eventsphere.dto.ApiResponse;
import com.eventsphere.dto.BookingResponse;
import com.eventsphere.service.BookingService;
import com.eventsphere.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{reservationId}/confirm")
    public ResponseEntity<BookingResponse> confirm(@PathVariable("reservationId") Long reservationId) {
        return ResponseEntity.ok(bookingService.confirmBooking(reservationId, SecurityUtil.getCurrentUsername()));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancel(@PathVariable("id") Long id) {
        bookingService.cancelBooking(id, SecurityUtil.getCurrentUsername());
        return ResponseEntity.ok(new ApiResponse("Booking cancelled"));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> list() {
        return ResponseEntity.ok(bookingService.listBookings(SecurityUtil.getCurrentUsername()));
    }
}
