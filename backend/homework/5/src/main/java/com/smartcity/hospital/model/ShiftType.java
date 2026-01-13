package com.smartcity.hospital.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShiftType {
    private Long id;
    private String name;
    private String description;
    private Boolean active;
    private Long tenantId;
}
