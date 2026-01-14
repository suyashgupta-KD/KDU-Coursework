package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.ShiftRepository;
import com.hospital.staffing.repo.ShiftTypeRepository;
import com.hospital.staffing.entity.Shift;
import com.hospital.staffing.model.request.ShiftRequest;
import com.hospital.staffing.model.response.ShiftResponse;

@Service
@RequiredArgsConstructor
public class ShiftService {

    // 1.
    private final ShiftRepository shiftRepository;
    private final ShiftTypeRepository shiftTypeRepository;

    // 2.
    public ShiftResponse create(ShiftRequest request) {
        Shift shift = new Shift();
        shift.setShiftName(request.getShiftName());
        shift.setStartDate(request.getStartDate());
        shift.setEndDate(request.getEndDate());
        shift.setShiftType(
                shiftTypeRepository.findById(request.getShiftTypeId()).orElseThrow());

        Shift saved = shiftRepository.save(shift);

        ShiftResponse response = new ShiftResponse();
        response.setId(saved.getId());
        response.setShiftName(saved.getShiftName());
        response.setStartDate(saved.getStartDate());
        response.setEndDate(saved.getEndDate());
        response.setShiftTypeId(saved.getShiftType().getId());
        return response;
    }
}
