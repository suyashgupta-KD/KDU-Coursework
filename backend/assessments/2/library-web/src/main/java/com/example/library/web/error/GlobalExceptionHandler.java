package com.example.library.web.error;

import com.example.library.api.error.ErrorCode;
import com.example.library.api.error.ErrorDetail;
import com.example.library.api.error.ErrorResponse;
import com.example.library.service.exception.BookNotAvailableException;
import com.example.library.service.exception.ConcurrencyConflictException;
import com.example.library.service.exception.InvalidStateException;
import com.example.library.service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex,
      HttpServletRequest request) {
    List<ErrorDetail> details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::toDetail)
        .collect(Collectors.toList());

    return buildResponse(
        HttpStatus.BAD_REQUEST,
        ErrorCode.VALIDATION_ERROR,
        "Invalid request",
        details,
        request.getRequestURI());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      NotFoundException ex,
      HttpServletRequest request) {
    return buildResponse(
        HttpStatus.NOT_FOUND,
        ErrorCode.NOT_FOUND,
        ex.getMessage(),
        null,
        request.getRequestURI());
  }

  @ExceptionHandler(InvalidStateException.class)
  public ResponseEntity<ErrorResponse> handleInvalidState(
      InvalidStateException ex,
      HttpServletRequest request) {
    return buildResponse(
        HttpStatus.CONFLICT,
        ErrorCode.INVALID_STATE_TRANSITION,
        ex.getMessage(),
        null,
        request.getRequestURI());
  }

  @ExceptionHandler(BookNotAvailableException.class)
  public ResponseEntity<ErrorResponse> handleBookUnavailable(
      BookNotAvailableException ex,
      HttpServletRequest request) {
    return buildResponse(
        HttpStatus.CONFLICT,
        ErrorCode.BOOK_NOT_AVAILABLE,
        ex.getMessage(),
        null,
        request.getRequestURI());
  }

  /**
   * CRITICAL:
   * In full Spring MVC flow, exceptions are often wrapped in ServletException.
   * We must catch Throwable and unwrap the ROOT cause.
   */
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ErrorResponse> handleThrowable(
      Throwable ex,
      HttpServletRequest request) {

    Throwable root = ex;
    while (root.getCause() != null) {
      root = root.getCause();
    }

    if (root instanceof ConcurrencyConflictException
        || root instanceof OptimisticLockingFailureException) {

      return buildResponse(
          HttpStatus.CONFLICT,
          ErrorCode.CONCURRENT_MODIFICATION,
          "Concurrent modification detected",
          null,
          request.getRequestURI());
    }

    return buildResponse(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ErrorCode.INTERNAL_ERROR,
        "Unexpected error",
        null,
        request.getRequestURI());
  }

  private ResponseEntity<ErrorResponse> buildResponse(
      HttpStatus status,
      ErrorCode code,
      String message,
      List<ErrorDetail> details,
      String path) {
    ErrorResponse response = new ErrorResponse();
    response.setTimestamp(Instant.now());
    response.setPath(path);
    response.setErrorCode(code);
    response.setMessage(message);

    if (details != null) {
      response.setDetails(details);
    }

    response.setCorrelationId(MDC.get("correlationId"));
    return ResponseEntity.status(status).body(response);
  }

  private ErrorDetail toDetail(FieldError error) {
    return new ErrorDetail(error.getField(), error.getDefaultMessage());
  }
}
