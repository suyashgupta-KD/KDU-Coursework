package com.railway.booking_system.service;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.InMemoryBroker;
import com.railway.booking_system.messaging.message.MessageTypes;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final InMemoryBroker broker;

    public BookingService(InMemoryBroker broker) {
        this.broker = broker;
    }

    public String createBooking(TicketBookedDto request) {
        // emulate saving booking to DB quickly (omitted)
        // publish event and return immediately
        broker.publish(MessageTypes.TICKET_BOOKED, request);
        return request.bookingId;
    }
}
