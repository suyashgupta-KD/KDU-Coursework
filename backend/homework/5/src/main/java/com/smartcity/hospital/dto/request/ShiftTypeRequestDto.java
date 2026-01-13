package com.smartcity.hospital.dto.request;

import lombok.Data;

@Data
public class ShiftTypeRequestDto {
    private String name;
    private String description;
    private Boolean active;
    private Long tenantId;
}
