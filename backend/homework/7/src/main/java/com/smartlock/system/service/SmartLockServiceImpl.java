package com.smartlock.system.service;

import com.smartlock.system.annotation.AuditAccess;
import com.smartlock.system.annotation.TrackExecution;
import com.smartlock.system.dto.response.UnlockResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class SmartLockServiceImpl implements SmartLockService {

    @Override
    @AuditAccess
    public UnlockResponseDTO unlock(String user) {
        System.out.println("The door is now open for " + user);
        return new UnlockResponseDTO("Door opened successfully", user);
    }

    @Override
    @TrackExecution
    public void checkBattery() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {
        }
        System.out.println("Battery level is OK");
    }
}
