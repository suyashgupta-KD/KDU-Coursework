package com.hospital.staffing.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShiftTypeRequest {
    @NotNull
    private String name;
}
