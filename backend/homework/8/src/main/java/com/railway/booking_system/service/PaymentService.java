package com.railway.booking_system.service;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.messaging.broker.SimpleQueue;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final SimpleQueue simpleQueue;

    public PaymentService(SimpleQueue simpleQueue) {
        this.simpleQueue = simpleQueue;
    }

    public void pushPayment(PaymentDto p) {
        simpleQueue.push(p);
    }
}
