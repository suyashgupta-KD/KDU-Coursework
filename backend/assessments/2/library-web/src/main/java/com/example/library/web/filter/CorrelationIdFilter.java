package com.example.library.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Ensures each request has a correlation ID in MDC and response headers.
 */
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {
  public static final String HEADER_NAME = "X-Correlation-Id";
  public static final String MDC_KEY = "correlationId";

  private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String correlationId = request.getHeader(HEADER_NAME);
    if (correlationId == null || correlationId.isBlank()) {
      correlationId = UUID.randomUUID().toString();
    }

    long start = System.currentTimeMillis();
    MDC.put(MDC_KEY, correlationId);
    response.setHeader(HEADER_NAME, correlationId);

    try {
      log.info("request.start method={} path={} correlationId={}", request.getMethod(),
          request.getRequestURI(), correlationId);
      filterChain.doFilter(request, response);
    } finally {
      long duration = System.currentTimeMillis() - start;
      log.info("request.end method={} path={} status={} latencyMs={} correlationId={}",
          request.getMethod(), request.getRequestURI(), response.getStatus(), duration, correlationId);
      MDC.remove(MDC_KEY);
    }
  }
}
