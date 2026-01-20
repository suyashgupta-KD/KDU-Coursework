package com.railway.booking_system.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "processed_payments", uniqueConstraints = @UniqueConstraint(columnNames = { "paymentId" }))
public class ProcessedPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentId;

    private Instant processedAt;

    public ProcessedPayment() {
    }

    public ProcessedPayment(String paymentId, Instant processedAt) {
        this.paymentId = paymentId;
        this.processedAt = processedAt;
    }

    // getters and setters omitted for brevity
    public String getPaymentId() {
        return paymentId;
    }
}
