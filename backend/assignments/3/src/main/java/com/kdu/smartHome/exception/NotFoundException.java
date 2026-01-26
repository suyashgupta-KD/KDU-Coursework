package com.kdu.smartHome.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for missing resources.
 */

public class NotFoundException extends AppException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
