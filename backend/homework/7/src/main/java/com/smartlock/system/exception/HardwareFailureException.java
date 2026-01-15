package com.smartlock.system.exception;

/**
 * Thrown when a hardware-level failure occurs
 * during lock operations.
 */
public class HardwareFailureException extends RuntimeException {

    public HardwareFailureException(String message) {
        super(message);
    }
}
