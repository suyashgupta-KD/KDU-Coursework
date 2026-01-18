package com.example.library.api.error;

/**
 * Standard error codes returned by the API.
 */
public enum ErrorCode {
  VALIDATION_ERROR,
  NOT_FOUND,
  INVALID_STATE_TRANSITION,
  BOOK_NOT_AVAILABLE,
  CONCURRENT_MODIFICATION,
  UNAUTHORIZED,
  FORBIDDEN,
  INTERNAL_ERROR
}
