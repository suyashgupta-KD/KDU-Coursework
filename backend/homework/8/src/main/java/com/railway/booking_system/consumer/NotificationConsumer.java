package com.railway.booking_system.consumer;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.InMemoryBroker;
import com.railway.booking_system.messaging.message.MessageTypes;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
public class NotificationConsumer {

    private final InMemoryBroker broker;

    public NotificationConsumer(InMemoryBroker broker) {
        this.broker = broker;
    }

    @PostConstruct
    public void subscribe() {
        Consumer<Object> handler = (obj) -> {
            if (obj instanceof TicketBookedDto) {
                TicketBookedDto t = (TicketBookedDto) obj;
                System.out.println("[Notification] Received TicketBooked: bookingId=" + t.bookingId +
                        ", sending SMS to " + t.passengerPhone);
                // call SMS provider asynchronously (with retry etc)
            }
        };
        broker.getOrCreateTopic(MessageTypes.TICKET_BOOKED).subscribe(handler);
    }
}
