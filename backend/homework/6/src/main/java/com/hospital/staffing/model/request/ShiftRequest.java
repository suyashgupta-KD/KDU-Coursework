package com.hospital.staffing.model.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ShiftRequest {

    // 1.
    private String shiftName;

    // 2.
    private LocalDate startDate;

    // 3.
    private LocalDate endDate;

    // 4.
    private Long shiftTypeId;
}
