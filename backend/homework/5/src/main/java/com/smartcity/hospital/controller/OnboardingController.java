package com.smartcity.hospital.controller;

import com.smartcity.hospital.dto.request.TenantOnboardingRequestDto;
import com.smartcity.hospital.model.Shift;
import com.smartcity.hospital.model.ShiftType;
import com.smartcity.hospital.model.User;
import com.smartcity.hospital.service.TenantOnboardingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/onboarding")
public class OnboardingController {

    private final TenantOnboardingService service;

    public OnboardingController(TenantOnboardingService service) {
        this.service = service;
    }

    @PostMapping
    public void onboard(@RequestBody TenantOnboardingRequestDto request) {

        ShiftType shiftType = new ShiftType();
        shiftType.setName(request.getShiftType().getName());
        shiftType.setDescription(request.getShiftType().getDescription());
        shiftType.setActive(request.getShiftType().getActive());
        shiftType.setTenantId(request.getShiftType().getTenantId());

        Shift shift = new Shift();
        shift.setShiftTypeId(request.getShift().getShiftTypeId());
        shift.setStartDate(request.getShift().getStartDate());
        shift.setEndDate(request.getShift().getEndDate());
        shift.setStartTime(request.getShift().getStartTime());
        shift.setEndTime(request.getShift().getEndTime());
        shift.setTenantId(request.getShift().getTenantId());

        User user = new User();
        user.setUsername(request.getUser().getUsername());
        user.setTimezone(request.getUser().getTimezone());
        user.setTenantId(request.getUser().getTenantId());

        service.onboardTenant(
                shiftType,
                shift,
                user,
                request.isFailAtEnd());
    }
}
