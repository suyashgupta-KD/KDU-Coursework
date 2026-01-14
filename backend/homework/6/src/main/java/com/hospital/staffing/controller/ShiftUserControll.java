package com.hospital.staffing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.staffing.model.request.ShiftUserRequest;
import com.hospital.staffing.model.response.ShiftUserResponse;
import com.hospital.staffing.service.ShiftUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shift-users")
@RequiredArgsConstructor
public class ShiftUserControll {

    // 1.
    private final ShiftUserService service;

    // 2.
    @PostMapping
    public ShiftUserResponse create(@Valid @RequestBody ShiftUserRequest request) {
        return service.create(request);
    }
}
