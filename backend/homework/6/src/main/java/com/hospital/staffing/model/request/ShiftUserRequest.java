package com.hospital.staffing.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShiftUserRequest {

    // 1.
    @NotNull
    private Long userId;

    // 2.
    @NotNull
    private Long shiftId;
}
