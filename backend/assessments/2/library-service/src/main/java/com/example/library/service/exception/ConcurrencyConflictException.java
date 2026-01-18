package com.example.library.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConcurrencyConflictException extends RuntimeException {

  public ConcurrencyConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConcurrencyConflictException(String message) {
    super(message);
  }
}
