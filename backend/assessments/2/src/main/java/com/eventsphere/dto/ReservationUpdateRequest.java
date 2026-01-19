package com.eventsphere.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationUpdateRequest {

    @Min(1)
    private int quantity;
}
