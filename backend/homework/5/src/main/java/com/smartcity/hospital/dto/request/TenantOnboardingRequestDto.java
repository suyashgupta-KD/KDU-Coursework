package com.smartcity.hospital.dto.request;

import lombok.Data;

@Data
public class TenantOnboardingRequestDto {

    private ShiftTypeRequestDto shiftType;
    private ShiftRequestDto shift;
    private UserRequestDto user;

    // controls rollback demo
    private boolean failAtEnd;
}
