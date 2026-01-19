package com.eventsphere.service;

import com.eventsphere.dto.ReservationRequest;
import com.eventsphere.dto.ReservationResponse;
import com.eventsphere.dto.ReservationUpdateRequest;
import com.eventsphere.entity.Event;
import com.eventsphere.entity.Reservation;
import com.eventsphere.entity.User;
import com.eventsphere.exception.ApiException;
import com.eventsphere.model.BookingStatus;
import com.eventsphere.model.ReservationStatus;
import com.eventsphere.repository.BookingRepository;
import com.eventsphere.repository.EventRepository;
import com.eventsphere.repository.ReservationRepository;
import com.eventsphere.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);
    private static final int amount = 100;
    private final ReservationRepository reservationRepository;
    private final EventService eventService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;

    public ReservationService(ReservationRepository reservationRepository,
            EventService eventService,
            UserRepository userRepository,
            EventRepository eventRepository,
            BookingRepository bookingRepository) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public ReservationResponse createReservation(Long eventId, ReservationRequest request, String username) {
        User user = getUser(username);
        Event event = eventService.getEventForUpdate(eventId);
        if (event.getAvailableTickets() < request.getQuantity()) {
            throw new ApiException("Not enough tickets available", HttpStatus.BAD_REQUEST);
        }
        event.setAvailableTickets(event.getAvailableTickets() - request.getQuantity());
        event.markUpdated();
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setQuantity(request.getQuantity());
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservationRepository.save(reservation);
        eventRepository.save(event);
        createPendingBooking(reservation);
        log.info("User {} reserved {} tickets for event {}", username, request.getQuantity(), event.getName());
        return toResponse(reservation);
    }

    @Transactional
    public ReservationResponse updateReservation(Long eventId, Long reservationId, ReservationUpdateRequest request,
            String username) {
        Reservation reservation = reservationRepository
                .findByIdAndUserIdAndEventId(reservationId, getUser(username).getId(), eventId)
                .orElseThrow(() -> new ApiException("Reservation not found for this event", HttpStatus.NOT_FOUND));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new ApiException("Reservation cannot be updated", HttpStatus.BAD_REQUEST);
        }
        Event event = eventService.getEventForUpdate(reservation.getEvent().getId());
        int delta = request.getQuantity() - reservation.getQuantity();
        if (delta > 0 && event.getAvailableTickets() < delta) {
            throw new ApiException("Not enough tickets available", HttpStatus.BAD_REQUEST);
        }
        int newAvailable = Math.min(event.getTotalTickets(), event.getAvailableTickets() - delta);
        event.setAvailableTickets(newAvailable);
        event.markUpdated();
        reservation.setQuantity(request.getQuantity());
        reservation.markUpdated();
        reservationRepository.save(reservation);
        eventRepository.save(event);
        bookingRepository.findByReservationId(reservation.getId()).ifPresent(booking -> {
            booking.setQuantity(reservation.getQuantity());
            bookingRepository.save(booking);
        });
        log.info("Updated reservation {} for user {}", reservationId, username);
        return toResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long eventId, Long reservationId, String username) {
        Reservation reservation = reservationRepository
                .findByIdAndUserIdAndEventId(reservationId, getUser(username).getId(), eventId)
                .orElseThrow(() -> new ApiException("Reservation not found for this event", HttpStatus.NOT_FOUND));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new ApiException("Reservation cannot be deleted", HttpStatus.BAD_REQUEST);
        }
        Event event = eventService.getEventForUpdate(reservation.getEvent().getId());
        int reservedBefore = event.getTotalTickets() - event.getAvailableTickets();
        int reservedAfter = Math.max(0, reservedBefore - reservation.getQuantity());
        event.setAvailableTickets(event.getTotalTickets() - reservedAfter);
        event.markUpdated();
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.markUpdated();
        reservationRepository.save(reservation);
        eventRepository.save(event);
        bookingRepository.findByReservationId(reservation.getId()).ifPresent(booking -> {
            booking.setStatus(com.eventsphere.model.BookingStatus.CANCELLED);
            bookingRepository.save(booking);
        });
        log.info("Cancelled reservation {} for user {}", reservationId, username);
    }

    public List<ReservationResponse> listReservations(Long eventId, String username) {
        User user = getUser(username);
        return reservationRepository.findByUserIdAndEventId(user.getId(), eventId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private void createPendingBooking(Reservation reservation) {
        bookingRepository.findByReservationId(reservation.getId()).ifPresentOrElse(
                b -> {
                },
                () -> {
                    com.eventsphere.entity.Booking booking = new com.eventsphere.entity.Booking();
                    booking.setReservation(reservation);
                    booking.setEvent(reservation.getEvent());
                    booking.setUser(reservation.getUser());
                    booking.setQuantity(reservation.getQuantity());
                    booking.setStatus(BookingStatus.PENDING);
                    booking.setAmount(amount);
                    bookingRepository.save(booking);
                });
    }

    public Reservation getActiveReservation(Long reservationId, String username) {
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, getUser(username).getId())
                .orElseThrow(() -> new ApiException("Reservation not found", HttpStatus.NOT_FOUND));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new ApiException("Reservation not active", HttpStatus.BAD_REQUEST);
        }
        if (reservation.getEvent().isDeleted()) {
            throw new ApiException("Event is deleted", HttpStatus.BAD_REQUEST);
        }
        return reservation;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getEvent().getId(),
                reservation.getEvent().getName(),
                reservation.getQuantity(),
                reservation.getStatus());
    }
}
