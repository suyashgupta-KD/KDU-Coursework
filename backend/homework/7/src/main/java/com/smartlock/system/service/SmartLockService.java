package com.smartlock.system.service;

import com.smartlock.system.dto.response.UnlockResponseDTO;

public interface SmartLockService {

    UnlockResponseDTO unlock(String user);
}
