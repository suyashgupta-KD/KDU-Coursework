package com.eventsphere.controller;

import com.eventsphere.dto.ApiResponse;
import com.eventsphere.dto.ReservationRequest;
import com.eventsphere.dto.ReservationResponse;
import com.eventsphere.dto.ReservationUpdateRequest;
import com.eventsphere.service.ReservationService;
import com.eventsphere.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public ResponseEntity<ReservationResponse> create(@PathVariable("eventId") Long eventId,
            @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(
                reservationService.createReservation(eventId, request, SecurityUtil.getCurrentUsername()));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(@PathVariable("eventId") Long eventId,
            @PathVariable("id") Long id,
            @Valid @RequestBody ReservationUpdateRequest request) {
        return ResponseEntity.ok(
                reservationService.updateReservation(eventId, id, request, SecurityUtil.getCurrentUsername()));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("eventId") Long eventId, @PathVariable("id") Long id) {
        reservationService.deleteReservation(eventId, id, SecurityUtil.getCurrentUsername());
        return ResponseEntity.ok(new ApiResponse("Reservation cancelled"));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> list(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok(reservationService.listReservations(eventId, SecurityUtil.getCurrentUsername()));
    }
}
