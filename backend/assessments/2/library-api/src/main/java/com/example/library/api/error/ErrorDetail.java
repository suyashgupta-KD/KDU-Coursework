package com.example.library.api.error;

/**
 * Field-level validation issue information.
 */
public class ErrorDetail {
  /**
   * Field name that failed validation.
   */
  private String field;
  /**
   * Human-readable validation issue.
   */
  private String issue;

  public ErrorDetail() {
  }

  public ErrorDetail(String field, String issue) {
    this.field = field;
    this.issue = issue;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }
}
