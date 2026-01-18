package com.example.library.service;

import com.example.library.domain.enums.BookStatus;
import java.util.Map;

/**
 * Read-only analytics for catalog status counts.
 */
public interface AnalyticsService {
  Map<BookStatus, Long> getAuditCounts();
}
