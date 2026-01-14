package com.hospital.staffing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.hospital.staffing.service.ShiftTypeService;

import jakarta.validation.Valid;

import com.hospital.staffing.model.request.ShiftTypeRequest;
import com.hospital.staffing.model.response.ShiftTypeResponse;

@RestController
@RequestMapping("/shift-types")
@RequiredArgsConstructor
public class ShiftTypeController {

    // 1.
    private final ShiftTypeService service;

    // 2.
    @PostMapping
    public ShiftTypeResponse create(@Valid @RequestBody ShiftTypeRequest request) {
        return service.create(request);
    }
}
