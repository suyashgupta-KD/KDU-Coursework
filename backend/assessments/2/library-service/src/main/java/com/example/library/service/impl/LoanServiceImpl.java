package com.example.library.service.impl;

import com.example.library.api.dto.LoanResponse;
import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.Loan;
import com.example.library.domain.entity.User;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.repo.BookRepository;
import com.example.library.domain.repo.LoanRepository;
import com.example.library.domain.repo.UserRepository;
import com.example.library.service.LoanService;
import com.example.library.service.exception.BookNotAvailableException;
import com.example.library.service.exception.ConcurrencyConflictException;
import com.example.library.service.exception.InvalidStateException;
import com.example.library.service.exception.NotFoundException;
import com.example.library.service.mapper.LoanMapper;
import java.time.Instant;
import java.util.UUID;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of loan lifecycle business logic.
 */
@Service
public class LoanServiceImpl implements LoanService {
  private final BookRepository bookRepository;
  private final LoanRepository loanRepository;
  private final UserRepository userRepository;
  private final LoanMapper loanMapper;

  public LoanServiceImpl(BookRepository bookRepository,
                         LoanRepository loanRepository,
                         UserRepository userRepository,
                         LoanMapper loanMapper) {
    this.bookRepository = bookRepository;
    this.loanRepository = loanRepository;
    this.userRepository = userRepository;
    this.loanMapper = loanMapper;
  }

  @Override
  @Transactional
  public LoanResponse borrowBook(UUID bookId, String username) {
    try {
      Book book = bookRepository.findById(bookId)
          .orElseThrow(() -> new NotFoundException("Book not found"));
      if (book.getStatus() != BookStatus.AVAILABLE) {
        throw new BookNotAvailableException("Book is not available");
      }
      if (loanRepository.existsByBookIdAndReturnedAtIsNull(bookId)) {
        throw new BookNotAvailableException("Book already has an active loan");
      }
      User borrower = userRepository.findByUsername(username)
          .orElseThrow(() -> new NotFoundException("User not found"));

      book.setStatus(BookStatus.CHECKED_OUT);

      Loan loan = new Loan();
      loan.setBook(book);
      loan.setBorrower(borrower);
      loan.setBorrowedAt(Instant.now());
      loan.setReturnedAt(null);

      Loan saved = loanRepository.save(loan);
      bookRepository.save(book);
      return loanMapper.toResponse(saved);
    } catch (OptimisticLockingFailureException ex) {
      throw new ConcurrencyConflictException("Book was modified concurrently", ex);
    }
  }

  @Override
  @Transactional
  public LoanResponse returnBook(UUID bookId, String username) {
    try {
      Book book = bookRepository.findById(bookId)
          .orElseThrow(() -> new NotFoundException("Book not found"));
      if (book.getStatus() != BookStatus.CHECKED_OUT) {
        throw new InvalidStateException("Book is not checked out");
      }

      Loan loan = loanRepository.findByBookIdAndReturnedAtIsNull(bookId)
          .orElseThrow(() -> new InvalidStateException("No active loan for this book"));
      if (!loan.getBorrower().getUsername().equals(username)) {
        throw new InvalidStateException("Only the borrower can return this book");
      }

      loan.setReturnedAt(Instant.now());
      book.setStatus(BookStatus.AVAILABLE);

      Loan saved = loanRepository.save(loan);
      bookRepository.save(book);
      return loanMapper.toResponse(saved);
    } catch (OptimisticLockingFailureException ex) {
      throw new ConcurrencyConflictException("Book was modified concurrently", ex);
    }
  }
}
