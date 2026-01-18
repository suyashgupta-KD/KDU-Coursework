package com.example.library.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request payload for creating a book in the catalog.
 */
public class CreateBookRequest {
  /**
   * Human-readable title of the book.
   */
  @NotBlank
  @Size(min = 2, max = 200)
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
