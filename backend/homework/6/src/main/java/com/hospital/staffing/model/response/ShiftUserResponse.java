package com.hospital.staffing.model.response;

import lombok.Data;

@Data
public class ShiftUserResponse {

    // 1.
    private Long id;

    // 2.
    private Long userId;

    // 3.
    private Long shiftId;
}
