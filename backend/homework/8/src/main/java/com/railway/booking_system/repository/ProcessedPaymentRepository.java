package com.railway.booking_system.repository;

import com.railway.booking_system.model.ProcessedPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProcessedPaymentRepository extends JpaRepository<ProcessedPayment, Long> {
    Optional<ProcessedPayment> findByPaymentId(String paymentId);
}
