package com.railway.booking_system.consumer;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.messaging.broker.ManualAckQueue;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PaymentConsumer {

    private final ManualAckQueue<PaymentDto> paymentQueue;

    // "Cache" of processed transactions (in-memory)
    private final Set<String> processedTxnIds = ConcurrentHashMap.newKeySet();

    public PaymentConsumer(ManualAckQueue<PaymentDto> paymentQueue) {
        this.paymentQueue = paymentQueue;
    }

    @PostConstruct
    public void start() {
        paymentQueue.start(handle -> {
            PaymentDto msg = handle.payload();

            // Idempotency check (must happen BEFORE side-effect)
            if (!processedTxnIds.add(msg.paymentId)) {
                System.out.println("[Payment] Transaction " + msg.paymentId +
                        " already processed. Ignoring duplicate.");
                handle.ack();
                return;
            }

            // Simulate processing
            System.out.println("[Payment] Money Deducted for transactionId=" + msg.paymentId +
                    " amount=" + msg.amount);

            // If something crashes BEFORE this ack, message will be redelivered
            handle.ack();
        });
    }
}
