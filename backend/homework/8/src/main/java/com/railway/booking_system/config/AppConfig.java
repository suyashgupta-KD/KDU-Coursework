package com.railway.booking_system.config;

import com.railway.booking_system.dto.TicketBookedDto;
import com.railway.booking_system.messaging.broker.RetryDlqQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RetryDlqQueue<TicketBookedDto> bookingMainQueue() {
        // 3 retries, 300ms delay (you can change delay)
        return new RetryDlqQueue<>("booking-main-queue", 3, 300);
    }
}
