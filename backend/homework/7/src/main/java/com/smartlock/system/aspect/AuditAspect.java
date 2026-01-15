package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import com.smartlock.system.dto.log.AccessLogDTO;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Before("@annotation(com.smartlock.system.annotation.AuditAccess)")
    public void logAccessAttempt(JoinPoint joinPoint) {
        String user = (String) joinPoint.getArgs()[0];

        AccessLogDTO logDTO = new AccessLogDTO(user, "ACCESS ATTEMPT: User is approaching the door");

        log.info("{} - {}", logDTO.getUser(), logDTO.getMessage());
    }

    @AfterReturning("@annotation(com.smartlock.system.annotation.AuditAccess)")
    public void logAccessSuccess(JoinPoint joinPoint) {
        String user = (String) joinPoint.getArgs()[0];

        AccessLogDTO logDTO = new AccessLogDTO(user, "SUCCESS: User has entered the building");

        log.info("{} - {}", logDTO.getUser(), logDTO.getMessage());
    }
}
