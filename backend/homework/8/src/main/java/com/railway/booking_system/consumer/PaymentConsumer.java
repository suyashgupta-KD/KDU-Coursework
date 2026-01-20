package com.railway.booking_system.consumer;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.messaging.broker.SimpleQueue;
import com.railway.booking_system.model.ProcessedPayment;
import com.railway.booking_system.repository.ProcessedPaymentRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.function.Consumer;

@Component
public class PaymentConsumer {

    private final SimpleQueue simpleQueue;
    private final ProcessedPaymentRepository repo;

    public PaymentConsumer(SimpleQueue simpleQueue, ProcessedPaymentRepository repo) {
        this.simpleQueue = simpleQueue;
        this.repo = repo;
    }

    @PostConstruct
    public void start() {
        Consumer<Object> handler = (obj) -> {
            if (obj instanceof PaymentDto) {
                PaymentDto p = (PaymentDto) obj;
                processPayment(p);
            }
        };
        simpleQueue.start(handler);
    }

    private void processPayment(PaymentDto p) {
        try {
            repo.save(new ProcessedPayment(p.paymentId, Instant.now()));
            System.out.println("[Payment] Money Deducted for paymentId=" + p.paymentId + " amount=" + p.amount);
        } catch (DataIntegrityViolationException dup) {
            // expected when paymentId already exists
            System.out.println("[Payment] Duplicate payment ignored: paymentId=" + p.paymentId);
        }
    }
}
