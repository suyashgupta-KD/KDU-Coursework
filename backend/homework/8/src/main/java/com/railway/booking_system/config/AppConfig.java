package com.railway.booking_system.config;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.ManualAckQueue;
import com.railway.booking_system.messaging.broker.RetryDlqQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Exercise 3: Payment queue with manual acknowledgments (at-least-once)
    @Bean
    public ManualAckQueue<PaymentDto> paymentQueue() {
        return new ManualAckQueue<>("payment-queue");
    }

    // Exercise 2: Booking queue with retry + DLQ
    @Bean
    public RetryDlqQueue<TicketBookedDto> bookingMainQueue() {
        return new RetryDlqQueue<>("booking-main-queue", 3, 300);
    }
}
