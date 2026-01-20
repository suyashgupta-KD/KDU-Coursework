package com.railway.booking_system.consumer;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.InMemoryBroker;
import com.railway.booking_system.messaging.message.MessageTypes;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.function.Consumer;

@Component
public class InventoryConsumer {

    private final InMemoryBroker broker;

    public InventoryConsumer(InMemoryBroker broker) {
        this.broker = broker;
    }

    @PostConstruct
    public void subscribe() {
        Consumer<Object> handler = (obj) -> {
            if (obj instanceof TicketBookedDto) {
                TicketBookedDto t = (TicketBookedDto) obj;
                System.out.println(
                        "[Inventory] Received TicketBooked: bookingId=" + t.bookingId + ", seat=" + t.seatNumber);
                // simulate marking seat occupied (DB call)
            }
        };
        broker.getOrCreateTopic(MessageTypes.TICKET_BOOKED).subscribe(handler);
    }
}
