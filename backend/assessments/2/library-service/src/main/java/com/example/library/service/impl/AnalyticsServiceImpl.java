package com.example.library.service.impl;

import com.example.library.domain.entity.Book;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repo.BookRepository;
import com.example.library.service.AnalyticsService;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of audit analytics queries.
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {
  private final BookRepository bookRepository;

  public AnalyticsServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Map<BookStatus, Long> getAuditCounts() {
    return bookRepository.findAll().stream()
        .collect(Collectors.groupingBy(Book::getStatus, Collectors.counting()));
  }
}
