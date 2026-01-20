package com.railway.booking_system.dto;

import java.io.Serializable;

public class PaymentDto implements Serializable {
    public String paymentId;
    public String bookingId;
    public double amount;

    public PaymentDto() {
    }

    public PaymentDto(String paymentId, String bookingId, double amount) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
    }
}
