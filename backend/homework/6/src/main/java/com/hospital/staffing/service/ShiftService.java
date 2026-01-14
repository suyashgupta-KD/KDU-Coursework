package com.hospital.staffing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.hospital.staffing.repo.ShiftRepository;
import com.hospital.staffing.entity.Shift;
import com.hospital.staffing.model.response.ShiftResponse;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    // 1.
    private final ShiftRepository repository;

    // 2.
    public List<ShiftResponse> getTop3NewYearShifts() {

        List<Shift> shifts = repository.findTopShifts(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 25),
                PageRequest.of(0, 3));

        // 3.
        return shifts.stream().map(shift -> {
            ShiftResponse dto = new ShiftResponse();
            dto.setId(shift.getId());
            dto.setShiftName(shift.getShiftName());
            dto.setStartDate(shift.getStartDate());
            dto.setEndDate(shift.getEndDate());
            return dto;
        }).toList();
    }
}
