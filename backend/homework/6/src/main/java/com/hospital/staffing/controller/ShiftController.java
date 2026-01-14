package com.hospital.staffing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.staffing.model.request.ShiftRequest;
import com.hospital.staffing.model.response.ShiftResponse;
import com.hospital.staffing.service.ShiftService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shifts")
@RequiredArgsConstructor
public class ShiftController {

    // 1.
    private final ShiftService service;

    // 2.
    @PostMapping
    public ShiftResponse create(@RequestBody ShiftRequest request) {
        return service.create(request);
    }
}
