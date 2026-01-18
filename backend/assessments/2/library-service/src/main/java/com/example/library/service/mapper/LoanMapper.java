package com.example.library.service.mapper;

import com.example.library.api.dto.LoanResponse;
import com.example.library.domain.entity.Loan;
import org.springframework.stereotype.Component;

/**
 * Maps loan entities to API responses.
 */
@Component
public class LoanMapper {
  public LoanResponse toResponse(Loan loan) {
    LoanResponse response = new LoanResponse();
    response.setId(loan.getId());
    response.setBookId(loan.getBook().getId());
    response.setBorrowerId(loan.getBorrower().getId());
    response.setBorrowedAt(loan.getBorrowedAt());
    response.setReturnedAt(loan.getReturnedAt());
    return response;
  }
}
