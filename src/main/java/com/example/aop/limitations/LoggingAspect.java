package com.example.aop.limitations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.aop.limitations..*.*(..)) && !execution(* com.example.aop.limitations.DemoService.InnerClass.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        System.out.println("AOP拦截: " + joinPoint.getSignature().toShortString());
    }

    @Before("execution(* com.example.aop.limitations.DemoService.InnerClass.*(..))")
    public void logInnerClassMethodCall(JoinPoint joinPoint) {
        System.out.println("内部类方法AOP拦截(实际不会发生): " + joinPoint.getSignature().toShortString());
    }
}