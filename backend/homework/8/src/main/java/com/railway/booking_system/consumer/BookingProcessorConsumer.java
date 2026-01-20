package com.railway.booking_system.consumer;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.RetryDlqQueue;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class BookingProcessorConsumer {

    private final RetryDlqQueue<TicketBookedDto> bookingMainQueue;

    public BookingProcessorConsumer(RetryDlqQueue<TicketBookedDto> bookingMainQueue) {
        this.bookingMainQueue = bookingMainQueue;
    }

    @PostConstruct
    public void start() {
        bookingMainQueue.start(this::process);
    }

    private void process(TicketBookedDto msg) {
        if (msg.age < 0) {
            throw new IllegalArgumentException("Negative age: " + msg.age);
        }
        System.out.println("[BookingProcessor] processed bookingId=" + msg.bookingId + " age=" + msg.age);
    }
}
