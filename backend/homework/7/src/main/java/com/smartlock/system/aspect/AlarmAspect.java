package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Aspect responsible for detecting and reporting
 * system-level failures in the smart lock.
 *
 * Observes exceptions thrown by the service layer
 * and triggers alarm logs accordingly.
 */
@Slf4j
@Aspect
@Component
public class AlarmAspect {

    /**
     * Triggers an alarm when a service-layer exception occurs.
     *
     * @param joinPoint the join point where the exception was thrown
     * @param ex        the exception that triggered the alarm
     */

    @AfterThrowing(pointcut = "execution(* com.smartlock.system.service..*(..))", throwing = "ex")
    public void triggerAlarm(JoinPoint joinPoint, Exception ex) {

        log.error(
                "ALARM TRIGGERED: System error detected in {} : {}",
                joinPoint.getSignature().getName(),
                ex.getMessage());

        // mock emergency escalation
        log.error("Emergency services notified (mock)");
    }
}
