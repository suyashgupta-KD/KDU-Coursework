package com.example.library.web.controller;

import com.example.library.api.dto.BookResponse;
import com.example.library.api.dto.CreateBookRequest;
import com.example.library.domain.enums.BookStatus;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @Operation(summary = "Create a new book in PROCESSING state")
  @PostMapping
  public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
    BookResponse response = bookService.createBook(request);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(response.getId())
        .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Catalog a processing book into AVAILABLE status")
  @PatchMapping("/{id}/catalog")
  public ResponseEntity<BookResponse> catalogBook(@PathVariable UUID id) {
    return ResponseEntity.ok(bookService.catalogBook(id));
  }

  @GetMapping
  public ResponseEntity<Page<BookResponse>> listBooks(
      @RequestParam(required = false) BookStatus status,
      @RequestParam(required = false) String titleContains,
      @PageableDefault Pageable pageable) {
    return ResponseEntity.ok(bookService.listBooks(status, titleContains, pageable));
  }
}
