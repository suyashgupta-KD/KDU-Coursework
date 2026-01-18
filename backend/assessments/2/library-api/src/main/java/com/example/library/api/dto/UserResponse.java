package com.example.library.api.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Response payload representing a user without credentials.
 */
public class UserResponse {
  /**
   * Unique identifier of the user.
   */
  private UUID id;
  /**
   * Username used for authentication.
   */
  private String username;
  /**
   * Assigned role name.
   */
  private String role;
  /**
   * Whether the account is enabled.
   */
  private boolean enabled;
  /**
   * When the user was created.
   */
  private Instant createdAt;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
