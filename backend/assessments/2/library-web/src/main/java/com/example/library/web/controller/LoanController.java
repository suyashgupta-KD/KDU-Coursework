package com.example.library.web.controller;

import com.example.library.api.dto.BorrowBookRequest;
import com.example.library.api.dto.LoanResponse;
import com.example.library.api.dto.ReturnBookRequest;
import com.example.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {
  private final LoanService loanService;

  public LoanController(LoanService loanService) {
    this.loanService = loanService;
  }

  @Operation(summary = "Borrow a book that is available")
  @PostMapping("/{bookId}/borrow")
  public ResponseEntity<LoanResponse> borrowBook(
      @PathVariable UUID bookId,
      @Valid @RequestBody(required = false) BorrowBookRequest request,
      Principal principal) {
    LoanResponse response = loanService.borrowBook(bookId, principal.getName());
    return ResponseEntity.status(201).body(response);
  }

  @Operation(summary = "Return a checked-out book")
  @PostMapping("/{bookId}/return")
  public ResponseEntity<LoanResponse> returnBook(
      @PathVariable UUID bookId,
      @Valid @RequestBody(required = false) ReturnBookRequest request,
      Principal principal) {
    return ResponseEntity.ok(loanService.returnBook(bookId, principal.getName()));
  }
}
