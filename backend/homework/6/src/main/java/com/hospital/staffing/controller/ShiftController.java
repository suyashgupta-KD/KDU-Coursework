package com.hospital.staffing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.hospital.staffing.service.ShiftService;
import com.hospital.staffing.model.response.ShiftResponse;

import java.util.List;

@RestController
@RequestMapping("/shifts")
@RequiredArgsConstructor
public class ShiftController {

    // 1.
    private final ShiftService service;

    // 2.
    @GetMapping("/top-new-year")
    public List<ShiftResponse> getTopNewYearShifts() {
        return service.getTop3NewYearShifts();
    }
}
