package com.eventsphere.service;

import com.eventsphere.dto.EventRequest;
import com.eventsphere.dto.EventResponse;
import com.eventsphere.dto.EventUpdateRequest;
import com.eventsphere.entity.Event;
import com.eventsphere.exception.ApiException;
import com.eventsphere.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponse createEvent(EventRequest request) {
        if (eventRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ApiException("Event name already exists", HttpStatus.BAD_REQUEST);
        }
        Event event = new Event();
        event.setName(request.getName());
        event.setTotalTickets(request.getTicketCount());
        event.setAvailableTickets(request.getTicketCount());
        Event saved = eventRepository.save(event);
        log.info("Created event {} with {} tickets", saved.getName(), saved.getTotalTickets());
        return toResponse(saved);
    }

    @Transactional
    public EventResponse updateTicketCount(Long eventId, EventUpdateRequest request) {
        Event event = getEventForUpdate(eventId);
        int reservedCount = event.getTotalTickets() - event.getAvailableTickets();
        int newTotal = request.getTicketCount();
        if (newTotal < reservedCount) {
            throw new ApiException("New ticket count cannot be less than already reserved/booked seats", HttpStatus.BAD_REQUEST);
        }
        event.setTotalTickets(newTotal);
        event.setAvailableTickets(newTotal - reservedCount);
        event.markUpdated();
        Event saved = eventRepository.save(event);
        log.info("Updated tickets for event {} to {}", saved.getName(), saved.getTotalTickets());
        return toResponse(saved);
    }

    public Page<EventResponse> listEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findByDeletedFalseAndAvailableTicketsGreaterThan(0, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public void softDelete(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException("Event not found", HttpStatus.NOT_FOUND));
        if (event.isDeleted()) {
            return;
        }
        event.setDeleted(true);
        event.setDeletedAt(LocalDateTime.now());
        event.markUpdated();
        eventRepository.save(event);
        log.info("Soft deleted event {}", event.getName());
    }

    @Transactional
    public Event getEventForUpdate(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException("Event not found", HttpStatus.NOT_FOUND));
        if (event.isDeleted()) {
            throw new ApiException("Event is deleted", HttpStatus.BAD_REQUEST);
        }
        return event;
    }

    @Transactional(readOnly = true)
    public Event getEventAllowDeleted(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException("Event not found", HttpStatus.NOT_FOUND));
    }

    public Event getActiveEvent(Long eventId) {
        return eventRepository.findByIdAndDeletedFalse(eventId)
                .orElseThrow(() -> new ApiException("Event not found", HttpStatus.NOT_FOUND));
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getTotalTickets(),
                event.getAvailableTickets(),
                event.isDeleted()
        );
    }
}
