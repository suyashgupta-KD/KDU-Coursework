package com.example.library.api.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Response payload representing a loan record.
 */
public class LoanResponse {
  /**
   * Unique identifier of the loan.
   */
  private UUID id;
  /**
   * Identifier of the borrowed book.
   */
  private UUID bookId;
  /**
   * Identifier of the borrower.
   */
  private UUID borrowerId;
  /**
   * When the loan started.
   */
  private Instant borrowedAt;
  /**
   * When the loan was closed, if returned.
   */
  private Instant returnedAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getBookId() {
    return bookId;
  }

  public void setBookId(UUID bookId) {
    this.bookId = bookId;
  }

  public UUID getBorrowerId() {
    return borrowerId;
  }

  public void setBorrowerId(UUID borrowerId) {
    this.borrowerId = borrowerId;
  }

  public Instant getBorrowedAt() {
    return borrowedAt;
  }

  public void setBorrowedAt(Instant borrowedAt) {
    this.borrowedAt = borrowedAt;
  }

  public Instant getReturnedAt() {
    return returnedAt;
  }

  public void setReturnedAt(Instant returnedAt) {
    this.returnedAt = returnedAt;
  }
}
