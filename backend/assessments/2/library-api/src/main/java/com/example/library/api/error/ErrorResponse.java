package com.example.library.api.error;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Standard error response returned by the API.
 */
public class ErrorResponse {
  /**
   * When the error occurred.
   */
  private Instant timestamp;
  /**
   * Request path that caused the error.
   */
  private String path;
  /**
   * Standardized error code.
   */
  private ErrorCode errorCode;
  /**
   * Human-readable summary of the error.
   */
  private String message;
  /**
   * Optional field-level details.
   */
  private List<ErrorDetail> details = new ArrayList<>();
  /**
   * Correlation ID associated with the request.
   */
  private String correlationId;

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<ErrorDetail> getDetails() {
    return details;
  }

  public void setDetails(List<ErrorDetail> details) {
    this.details = details;
  }

  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }
}
