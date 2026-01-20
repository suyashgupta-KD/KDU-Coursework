package com.railway.booking_system.config;

import com.railway.booking_system.messaging.broker.SimpleQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SimpleQueue paymentQueue() {
        return new SimpleQueue(); // beans may be reused by injection
    }
}
