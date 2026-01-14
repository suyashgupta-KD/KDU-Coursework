package com.hospital.staffing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.staffing.model.request.UserRequest;
import com.hospital.staffing.model.response.UserResponse;
import com.hospital.staffing.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 1.
    private final UserService service;

    // 2.
    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return service.create(request);
    }
}
