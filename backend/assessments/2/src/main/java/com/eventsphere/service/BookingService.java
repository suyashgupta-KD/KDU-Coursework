package com.eventsphere.service;

import com.eventsphere.dto.BookingResponse;
import com.eventsphere.entity.Booking;
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

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final int DEFAULT_AMOUNT = 100;

    private final BookingRepository bookingRepository;
    private final ReservationService reservationService;
    private final EventService eventService;
    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          ReservationService reservationService,
                          EventService eventService,
                          ReservationRepository reservationRepository,
                          EventRepository eventRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.reservationService = reservationService;
        this.eventService = eventService;
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse confirmBooking(Long reservationId, String username) {
        Reservation reservation = reservationService.getActiveReservation(reservationId, username);
        Booking booking = bookingRepository.findByReservationId(reservation.getId()).orElse(null);
        if (booking != null && booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new ApiException("Booking already confirmed for this reservation", HttpStatus.BAD_REQUEST);
        }
        Event event = eventService.getEventForUpdate(reservation.getEvent().getId());
        if (event.isDeleted()) {
            throw new ApiException("Cannot confirm booking for deleted event", HttpStatus.BAD_REQUEST);
        }
        if (booking == null) {
            booking = new Booking();
            booking.setReservation(reservation);
            booking.setEvent(event);
            booking.setUser(reservation.getUser());
            booking.setQuantity(reservation.getQuantity());
            booking.setAmount(DEFAULT_AMOUNT);
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTransactionId(UUID.randomUUID().toString());
        booking.setTransactionDate(java.time.LocalDateTime.now());
        booking.setAmount(DEFAULT_AMOUNT);
        booking.setQuantity(reservation.getQuantity());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.markUpdated();
        reservationRepository.save(reservation);
        bookingRepository.save(booking);
        log.info("Booking confirmed for user {} (booking id {})", username, booking.getId());
        return toResponse(booking);
    }

    @Transactional
    public void cancelBooking(Long bookingId, String username) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, getUser(username).getId())
                .orElseThrow(() -> new ApiException("Booking not found", HttpStatus.NOT_FOUND));
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ApiException("Booking already cancelled", HttpStatus.BAD_REQUEST);
        }
        Event event = eventService.getEventAllowDeleted(booking.getEvent().getId());
        int reservedBefore = event.getTotalTickets() - event.getAvailableTickets();
        int reservedAfter = Math.max(0, reservedBefore - booking.getQuantity());
        event.setAvailableTickets(event.getTotalTickets() - reservedAfter);
        event.markUpdated();
        booking.setStatus(BookingStatus.CANCELLED);
        Reservation reservation = booking.getReservation();
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.markUpdated();
        bookingRepository.save(booking);
        reservationRepository.save(reservation);
        eventRepository.save(event);
        log.info("Booking cancelled for user {} (booking id {})", username, bookingId);
    }

    public List<BookingResponse> listBookings(String username) {
        User user = getUser(username);
        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getReservation().getId(),
                booking.getEvent().getId(),
                booking.getEvent().getName(),
                booking.getQuantity(),
                booking.getStatus(),
                booking.getTransactionId(),
                booking.getTransactionDate() != null ? booking.getTransactionDate().format(ISO_FORMAT) : null,
                String.valueOf(booking.getAmount())
        );
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }
}
