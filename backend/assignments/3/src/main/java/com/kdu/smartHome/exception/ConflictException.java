package com.kdu.smartHome.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for conflicts.
 */

public class ConflictException extends AppException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
