package com.smartlock.system.controller;

import com.smartlock.system.dto.request.UnlockRequestDTO;
import com.smartlock.system.dto.response.UnlockResponseDTO;
import com.smartlock.system.service.SmartLockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lock")
@RequiredArgsConstructor

/**
 * REST controller exposing endpoints to interact with the smart lock system.
 *
 * Acts as the entry point for external clients and delegates
 * all business logic to the service layer.
 */
public class SmartLockController {

    private final SmartLockService smartLockService;

    /**
     * Unlocks the door for the given user.
     *
     * @param user identifier of the user attempting access
     * @return result of the unlock operation
     */
    @PostMapping("/unlock")
    public UnlockResponseDTO unlock(@RequestBody UnlockRequestDTO request) {
        return smartLockService.unlock(request.getUser());
    }

    /**
     * Checks the current battery status of the smart lock.
     */

    @GetMapping("/battery")
    public void checkBattery() {
        smartLockService.checkBattery();
    }
}
