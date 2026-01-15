package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Aspect responsible for enforcing security policies
 * such as access control and execution timing.
 *
 * Uses @Around advice to control method execution.
 */
@Slf4j
@Aspect
@Component
public class SecurityAspect {
    /**
     * Controls access to secured methods.
     *
     * Blocks execution if the user is unauthorized.
     *
     * @param pjp the proceeding join point
     * @return result of method execution or null if blocked
     * @throws Throwable if target method throws an exception
     */
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
