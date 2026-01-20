package com.railway.booking_system.service;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.messaging.broker.ManualAckQueue;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final ManualAckQueue<PaymentDto> paymentQueue;

    public PaymentService(ManualAckQueue<PaymentDto> paymentQueue) {
        this.paymentQueue = paymentQueue;
    }

    public void pushPayment(PaymentDto p) {
        paymentQueue.push(p);
    }
}
