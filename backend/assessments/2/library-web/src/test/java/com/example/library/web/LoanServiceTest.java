package com.example.library.web;

import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.User;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.enums.Role;
import com.example.library.domain.repo.BookRepository;
import com.example.library.domain.repo.UserRepository;
import com.example.library.service.LoanService;
import com.example.library.service.exception.BookNotAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class LoanServiceTest {
  @Autowired
  private LoanService loanService;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  void borrowAndReturnFlowUpdatesStatus() {
    User user = new User();
    user.setUsername("member1");
    user.setPassword("secret");
    user.setRole(Role.MEMBER);
    user.setEnabled(true);
    User savedUser = userRepository.save(user);

    Book book = new Book();
    book.setTitle("Borrowable");
    book.setStatus(BookStatus.AVAILABLE);
    Book savedBook = bookRepository.save(book);

    var borrow = loanService.borrowBook(savedBook.getId(), savedUser.getUsername());
    assertThat(borrow.getBookId()).isEqualTo(savedBook.getId());
    assertThat(bookRepository.findById(savedBook.getId()).orElseThrow().getStatus())
        .isEqualTo(BookStatus.CHECKED_OUT);

    var returned = loanService.returnBook(savedBook.getId(), savedUser.getUsername());
    assertThat(returned.getReturnedAt()).isNotNull();
    assertThat(bookRepository.findById(savedBook.getId()).orElseThrow().getStatus())
        .isEqualTo(BookStatus.AVAILABLE);
  }

  @Test
  void borrowFailsWhenBookNotAvailable() {
    User user = new User();
    user.setUsername("member2");
    user.setPassword("secret");
    user.setRole(Role.MEMBER);
    user.setEnabled(true);
    User savedUser = userRepository.save(user);

    Book book = new Book();
    book.setTitle("Unavailable");
    book.setStatus(BookStatus.PROCESSING);
    Book savedBook = bookRepository.save(book);

    assertThrows(BookNotAvailableException.class,
        () -> loanService.borrowBook(savedBook.getId(), savedUser.getUsername()));
  }
}
