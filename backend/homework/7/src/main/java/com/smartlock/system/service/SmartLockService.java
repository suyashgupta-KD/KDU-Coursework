package com.smartlock.system.service;

import com.smartlock.system.dto.response.UnlockResponseDTO;

/**
 * Contract defining operations supported by the smart lock.
 *
 * Implementations should contain only business logic and
 * must remain free of cross-cutting concerns.
 */
public interface SmartLockService {

    UnlockResponseDTO unlock(String user);

    void checkBattery();
}
