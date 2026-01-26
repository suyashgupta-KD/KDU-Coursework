package com.kdu.smartHome.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception for authentication failures.
 */

public class UnauthorizedException extends AppException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
