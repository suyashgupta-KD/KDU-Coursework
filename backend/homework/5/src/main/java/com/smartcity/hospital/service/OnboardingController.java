package com.smartcity.hospital.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartcity.hospital.model.Shift;
import com.smartcity.hospital.model.ShiftType;
import com.smartcity.hospital.model.User;

@RestController
@RequestMapping("/onboarding")
public class OnboardingController {

    private final TenantOnboardingService service;

    public OnboardingController(TenantOnboardingService service) {
        this.service = service;
    }

    @PostMapping
    public void onboard(@RequestParam boolean fail) {

        ShiftType shiftType = new ShiftType();
        shiftType.setName("Morning");
        shiftType.setDescription("Morning shift");
        shiftType.setActive(true);
        shiftType.setTenantId(1L);

        Shift shift = new Shift();
        shift.setShiftTypeId(1L);
        shift.setStartDate(LocalDate.now());
        shift.setEndDate(LocalDate.now());
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setTenantId(1L);

        User user = new User();
        user.setUsername("doctor_tx");
        user.setTimezone("Asia/Kolkata");
        user.setTenantId(1L);

        service.onboardTenant(shiftType, shift, user, fail);
    }

}
