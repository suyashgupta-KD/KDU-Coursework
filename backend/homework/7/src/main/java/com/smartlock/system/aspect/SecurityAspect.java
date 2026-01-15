package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SecurityAspect {

    // ACCESS CONTROL
    @Around("@annotation(com.smartlock.system.annotation.SecureAccess)")
    public Object guardDoor(ProceedingJoinPoint pjp) throws Throwable {

        String user = (String) pjp.getArgs()[0];

        if ("Unknown".equalsIgnoreCase(user)) {
            log.warn("SECURITY ALERT: Unauthorized access blocked!");
            return null; // proceed() NOT called
        }

        return pjp.proceed(); // allow execution
    }

    // STOPWATCH
    @Around("@annotation(com.smartlock.system.annotation.TrackExecution)")
    public Object trackExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = pjp.proceed();

        long end = System.currentTimeMillis();

        log.info("Execution time of {} : {} ms",
                pjp.getSignature().getName(),
                (end - start));

        return result;
    }
}
