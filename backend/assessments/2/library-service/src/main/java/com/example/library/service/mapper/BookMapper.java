package com.example.library.service.mapper;

import com.example.library.api.dto.BookResponse;
import com.example.library.domain.entity.Book;
import org.springframework.stereotype.Component;

/**
 * Maps book entities to API responses.
 */
@Component
public class BookMapper {
  public BookResponse toResponse(Book book) {
    BookResponse response = new BookResponse();
    response.setId(book.getId());
    response.setTitle(book.getTitle());
    response.setStatus(book.getStatus().name());
    response.setCreatedAt(book.getCreatedAt());
    response.setUpdatedAt(book.getUpdatedAt());
    response.setVersion(book.getVersion());
    return response;
  }
}
