package com.smartlock.system.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AlarmAspect {

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
