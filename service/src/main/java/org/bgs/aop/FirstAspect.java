package org.bgs.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class FirstAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Around("isServiceLayer()")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(parameter ->
                log.info("Parameter " + parameter + " was added into "
                        + joinPoint.getSignature().getName() + " method"));
        try {
            Object result = joinPoint.proceed();
            log.info
                    ("The method " + joinPoint.getSignature().getName() + " returned {} result", result);
            return result;
        } catch (Throwable ex) {
            log.info("AROUND after throwing - invoked method " + joinPoint.getSignature().getName() + ", exception {}", ex.getMessage());
            throw ex;
        }
    }
}
