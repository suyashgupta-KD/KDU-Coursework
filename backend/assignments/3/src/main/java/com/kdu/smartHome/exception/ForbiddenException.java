package com.kdu.smartHome.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for forbidden access.
 */

public class ForbiddenException extends AppException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
