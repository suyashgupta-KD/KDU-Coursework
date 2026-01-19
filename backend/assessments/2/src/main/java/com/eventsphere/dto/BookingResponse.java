package com.eventsphere.dto;

import com.eventsphere.model.BookingStatus;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private Long reservationId;
    private Long eventId;
    private String eventName;
    private int quantity;
    private BookingStatus status;
    private String transactionId;
    private String transactionDate;
    private String amount;
}
