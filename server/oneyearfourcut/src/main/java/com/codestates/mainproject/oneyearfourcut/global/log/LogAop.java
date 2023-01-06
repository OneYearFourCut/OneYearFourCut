package com.codestates.mainproject.oneyearfourcut.global.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAop {

    @Pointcut("execution(* com.codestates.mainproject.oneyearfourcut.domain.*.controller..*(..))")
    private void allController() {}

    @Around("allController()")
    public Object galleryLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("#################################################");
        log.info("#########{} -> {}##########", joinPoint.getSignature().getDeclaringType().getSimpleName(), joinPoint.getSignature().getName());
        log.info("#################################################");

        return joinPoint.proceed();
    }
}
