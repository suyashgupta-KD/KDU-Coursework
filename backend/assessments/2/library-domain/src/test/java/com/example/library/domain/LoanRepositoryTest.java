package com.example.library.domain;

import com.example.library.domain.entity.Book;
import com.example.library.domain.entity.Loan;
import com.example.library.domain.entity.User;
import com.example.library.domain.enums.BookStatus;
import com.example.library.domain.enums.Role;
import com.example.library.domain.repo.BookRepository;
import com.example.library.domain.repo.LoanRepository;
import com.example.library.domain.repo.UserRepository;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = DomainTestApplication.class)
@ActiveProfiles("test")
class LoanRepositoryTest {
  @Autowired
  private LoanRepository loanRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  void detectsActiveLoanForBook() {
    User user = new User();
    user.setUsername("member1");
    user.setPassword("secret");
    user.setRole(Role.MEMBER);
    user.setEnabled(true);
    user = userRepository.save(user);

    Book book = new Book();
    book.setTitle("Test Book");
    book.setStatus(BookStatus.AVAILABLE);
    book = bookRepository.save(book);

    Loan loan = new Loan();
    loan.setBook(book);
    loan.setBorrower(user);
    loan.setBorrowedAt(Instant.now());
    loan = loanRepository.save(loan);

    assertThat(loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId())).isTrue();
    loan.setReturnedAt(Instant.now());
    loanRepository.save(loan);
    assertThat(loanRepository.existsByBookIdAndReturnedAtIsNull(book.getId())).isFalse();
  }
}
