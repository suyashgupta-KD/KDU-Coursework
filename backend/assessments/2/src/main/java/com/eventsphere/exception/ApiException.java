package com.eventsphere.exception;

import org.springframework.http.HttpStatus;

import jakarta.validation.constraints.NotNull;

public class ApiException extends RuntimeException {

    @NotNull
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
