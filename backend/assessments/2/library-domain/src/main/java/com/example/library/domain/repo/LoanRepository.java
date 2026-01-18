package com.example.library.domain.repo;

import com.example.library.domain.entity.Loan;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
  Optional<Loan> findByBookIdAndReturnedAtIsNull(UUID bookId);

  boolean existsByBookIdAndReturnedAtIsNull(UUID bookId);
}
