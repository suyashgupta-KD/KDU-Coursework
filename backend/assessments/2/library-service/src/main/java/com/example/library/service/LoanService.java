package com.example.library.service;

import com.example.library.api.dto.LoanResponse;
import java.util.UUID;

/**
 * Business operations for borrowing and returning books.
 */
public interface LoanService {
  LoanResponse borrowBook(UUID bookId, String username);

  LoanResponse returnBook(UUID bookId, String username);
}
