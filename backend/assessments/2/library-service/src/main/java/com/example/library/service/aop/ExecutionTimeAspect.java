package com.example.library.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
  private static final Logger log = LoggerFactory.getLogger(ExecutionTimeAspect.class);

  @Around("execution(* com.example.library.service..*(..))")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      return joinPoint.proceed();
    } finally {
      long duration = System.currentTimeMillis() - start;
      log.info("service.method={} durationMs={}", joinPoint.getSignature().toShortString(), duration);
    }
  }
}
