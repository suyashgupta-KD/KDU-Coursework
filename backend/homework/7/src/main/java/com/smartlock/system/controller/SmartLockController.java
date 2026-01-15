package com.smartlock.system.controller;

import com.smartlock.system.dto.request.UnlockRequestDTO;
import com.smartlock.system.dto.response.UnlockResponseDTO;
import com.smartlock.system.service.SmartLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lock")
@RequiredArgsConstructor
public class SmartLockController {

    private final SmartLockService smartLockService;

    @PostMapping("/unlock")
    public UnlockResponseDTO unlock(@RequestBody UnlockRequestDTO request) {
        return smartLockService.unlock(request.getUser());
    }
}
