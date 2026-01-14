package com.hospital.staffing.model.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ShiftResponse {

    // 1.
    private Long id;

    // 2.
    private String shiftName;

    // 3.
    private LocalDate startDate;

    // 4.
    private LocalDate endDate;

    // 5.
    private Long shiftTypeId;
}
