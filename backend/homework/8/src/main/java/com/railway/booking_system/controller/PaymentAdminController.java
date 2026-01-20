package com.railway.booking_system.controller;

import com.railway.booking_system.dto.PaymentDto;
import com.railway.booking_system.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/payments")
public class PaymentAdminController {
    private final PaymentService paymentService;

    public PaymentAdminController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/push")
    public ResponseEntity<?> pushPayment(@RequestParam String paymentId,
            @RequestParam String bookingId,
            @RequestParam double amount) {
        PaymentDto p = new PaymentDto(paymentId, bookingId, amount);
        paymentService.pushPayment(p);
        return ResponseEntity.ok("Pushed");
    }
}
