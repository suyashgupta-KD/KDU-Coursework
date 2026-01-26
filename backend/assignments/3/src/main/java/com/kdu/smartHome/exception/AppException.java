package com.kdu.smartHome.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception with an HTTP status.
 */

public class AppException extends RuntimeException {
    private final HttpStatus status;

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
