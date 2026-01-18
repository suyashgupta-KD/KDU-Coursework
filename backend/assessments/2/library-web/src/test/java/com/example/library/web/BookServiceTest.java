package com.example.library.web;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repo.BookRepository;
import com.example.library.service.BookService;
import com.example.library.service.exception.InvalidStateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {
  @Autowired
  private BookService bookService;

  @Autowired
  private BookRepository bookRepository;

  @Test
  void catalogTransitionsProcessingToAvailable() {
    Book created = new Book();
    created.setTitle("Processing Book");
    created.setStatus(BookStatus.PROCESSING);
    created = bookRepository.save(created);

    var response = bookService.catalogBook(created.getId());
    assertThat(response.getStatus()).isEqualTo(BookStatus.AVAILABLE.name());
  }

  @Test
  void catalogRejectsInvalidState() {
    Book book = new Book();
    book.setTitle("Available Book");
    book.setStatus(BookStatus.AVAILABLE);
    Book saved = bookRepository.save(book);

    assertThrows(InvalidStateException.class, () -> bookService.catalogBook(saved.getId()));
  }
}
