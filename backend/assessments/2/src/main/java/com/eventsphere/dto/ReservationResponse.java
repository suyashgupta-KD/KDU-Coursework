package com.eventsphere.dto;

import com.eventsphere.model.ReservationStatus;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private Long id;
    private Long eventId;
    private String eventName;
    private int quantity;
    private ReservationStatus status;
}
