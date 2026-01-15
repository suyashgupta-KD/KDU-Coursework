package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import com.smartlock.system.dto.log.AccessLogDTO;

/**
 * Aspect responsible for auditing access attempts
 * to the smart lock system.
 *
 * Logs both entry attempts and successful accesses
 * without interfering with business logic.
 */
@Slf4j
@Aspect
@Component
public class AuditAspect {

    /**
     * Logs an access attempt before the target method executes.
     *
     * @param joinPoint the join point providing method context
     */

    @Before("@annotation(com.smartlock.system.annotation.AuditAccess)")
    public void logAccessAttempt(JoinPoint joinPoint) {
        String user = (String) joinPoint.getArgs()[0];

        AccessLogDTO logDTO = new AccessLogDTO(user, "ACCESS ATTEMPT: User is approaching the door");

        log.info("{} - {}", logDTO.getUser(), logDTO.getMessage());
    }

    /**
     * Logs a successful access after the target method
     * completes without throwing an exception.
     *
     * @param joinPoint the join point providing method context
     */
    @AfterReturning("@annotation(com.smartlock.system.annotation.AuditAccess)")
    public void logAccessSuccess(JoinPoint joinPoint) {
        String user = (String) joinPoint.getArgs()[0];

        AccessLogDTO logDTO = new AccessLogDTO(user, "SUCCESS: User has entered the building");

        log.info("{} - {}", logDTO.getUser(), logDTO.getMessage());
    }
}
