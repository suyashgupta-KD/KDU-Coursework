package com.example.library.service;

import com.example.library.api.dto.BookResponse;
import com.example.library.api.dto.CreateBookRequest;
import com.example.library.domain.enums.BookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

/**
 * Business operations for managing books and catalog state transitions.
 */
public interface BookService {
  BookResponse createBook(CreateBookRequest request);

  BookResponse catalogBook(UUID id);

  Page<BookResponse> listBooks(BookStatus status, String titleContains, Pageable pageable);
}
