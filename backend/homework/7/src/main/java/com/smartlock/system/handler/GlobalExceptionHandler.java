package com.smartlock.system.handler;

import com.smartlock.system.exception.HardwareFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Global exception handler responsible for translating
 * domain exceptions into HTTP responses.
 *
 * Ensures that internal errors do not leak stack traces
 * to API consumers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HardwareFailureException.class)
    public ResponseEntity<String> handleHardwareFailure(HardwareFailureException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("LOCK ERROR: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected system error");
    }
}
