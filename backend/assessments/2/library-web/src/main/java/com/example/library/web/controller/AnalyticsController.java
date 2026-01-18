package com.example.library.web.controller;

import com.example.library.domain.enums.BookStatus;
import com.example.library.service.AnalyticsService;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
  private final AnalyticsService analyticsService;

  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService;
  }

  @GetMapping("/audit")
  public ResponseEntity<Map<String, Long>> auditCounts() {
    Map<String, Long> response = analyticsService.getAuditCounts().entrySet().stream()
        .collect(Collectors.toMap(entry -> entry.getKey().name(), Map.Entry::getValue));
    return ResponseEntity.ok(response);
  }
}
