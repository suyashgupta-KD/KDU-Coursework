package com.railway.booking_system.dto;

import java.io.Serializable;

public class TicketBookedDto implements Serializable {
    public String bookingId;
    public String passengerPhone;
    public String seatNumber;
    public String journey;
    public int age;

    public TicketBookedDto() {
    }

    public TicketBookedDto(String bookingId, String passengerPhone, String seatNumber, String journey) {
        this.bookingId = bookingId;
        this.passengerPhone = passengerPhone;
        this.seatNumber = seatNumber;
        this.journey = journey;

    }
}
