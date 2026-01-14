package com.hospital.staffing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.hospital.staffing.service.UserService;
import com.hospital.staffing.model.response.UserPageResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // 1.
    private final UserService service;

    // 2.
    @GetMapping
    public UserPageResponse getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        return service.getUsers(page, size);
    }
}
