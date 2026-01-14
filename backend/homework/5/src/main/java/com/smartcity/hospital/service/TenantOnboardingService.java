package com.smartcity.hospital.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcity.hospital.model.Shift;
import com.smartcity.hospital.model.ShiftType;
import com.smartcity.hospital.model.User;
import com.smartcity.hospital.repository.ShiftJdbcRepository;
import com.smartcity.hospital.repository.ShiftTypeJdbcRepository;
import com.smartcity.hospital.repository.ShiftUserJdbcRepository;
import com.smartcity.hospital.repository.UserJdbcRepository;

@Service
public class TenantOnboardingService {

    private final ShiftTypeJdbcRepository shiftTypeRepo;
    private final ShiftJdbcRepository shiftRepo;
    private final UserJdbcRepository userRepo;
    private final ShiftUserJdbcRepository shiftUserRepo;

    public TenantOnboardingService(
            ShiftTypeJdbcRepository shiftTypeRepo,
            ShiftJdbcRepository shiftRepo,
            UserJdbcRepository userRepo,
            ShiftUserJdbcRepository shiftUserRepo) {
        this.shiftTypeRepo = shiftTypeRepo;
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
        this.shiftUserRepo = shiftUserRepo;
    }

    @Transactional
    public void onboardTenant(
            ShiftType shiftType,
            Shift shift,
            User user,
            boolean failAtEnd // 🔥 control rollback
    ) {

        // 1️⃣ Save shift type
        shiftTypeRepo.save(shiftType);

        // 2️⃣ Save shift
        shiftRepo.save(shift);

        // 3️⃣ Save user
        userRepo.save(
                user.getUsername(),
                user.getTimezone(),
                user.getTenantId());

        // 4️⃣ Assign user to shift (NOW reachable)
        shiftUserRepo.assignUserToShift(
                shift.getShiftTypeId(),
                1L, // temp user id for demo
                user.getTenantId());

        // Conditional failure (for rollback demo)
        if (failAtEnd) {
            throw new RuntimeException("Simulated failure – rollback test");
        }
    }
}
