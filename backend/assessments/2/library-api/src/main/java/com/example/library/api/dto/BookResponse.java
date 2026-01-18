package com.example.library.api.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Response payload representing a book in the catalog.
 */
public class BookResponse {
  /**
   * Unique identifier of the book.
   */
  private UUID id;
  /**
   * Title of the book.
   */
  private String title;
  /**
   * Current catalog status of the book.
   */
  private String status;
  /**
   * Creation timestamp in UTC.
   */
  private Instant createdAt;
  /**
   * Last update timestamp in UTC.
   */
  private Instant updatedAt;
  /**
   * Optimistic lock version for concurrency control.
   */
  private Long version;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
