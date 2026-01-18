package com.example.library.service.impl;

import com.example.library.api.dto.BookResponse;
import com.example.library.api.dto.CreateBookRequest;
import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repo.BookRepository;
import com.example.library.service.BookService;
import com.example.library.service.exception.ConcurrencyConflictException;
import com.example.library.service.exception.InvalidStateException;
import com.example.library.service.exception.NotFoundException;
import com.example.library.service.mapper.BookMapper;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of book management business logic.
 */
@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  @Override
  @Transactional
  public BookResponse createBook(CreateBookRequest request) {
    Book book = new Book();
    book.setTitle(request.getTitle());
    book.setStatus(BookStatus.PROCESSING);
    Book saved = bookRepository.save(book);
    return bookMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public BookResponse catalogBook(UUID id) {
    try {
      Book book = bookRepository.findById(id)
          .orElseThrow(() -> new NotFoundException("Book not found"));
      if (book.getStatus() != BookStatus.PROCESSING) {
        throw new InvalidStateException("Book is not in PROCESSING state");
      }
      book.setStatus(BookStatus.AVAILABLE);
      Book saved = bookRepository.save(book);
      return bookMapper.toResponse(saved);
    } catch (OptimisticLockingFailureException ex) {
      throw new ConcurrencyConflictException("Book was modified concurrently", ex);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<BookResponse> listBooks(BookStatus status, String titleContains, Pageable pageable) {
    Specification<Book> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (status != null) {
        predicates.add(cb.equal(root.get("status"), status));
      }
      if (titleContains != null && !titleContains.isBlank()) {
        predicates.add(cb.like(cb.lower(root.get("title")), "%" + titleContains.toLowerCase() + "%"));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };

    Page<Book> page = bookRepository.findAll(spec, pageable);
    return page.map(bookMapper::toResponse);
  }
}
