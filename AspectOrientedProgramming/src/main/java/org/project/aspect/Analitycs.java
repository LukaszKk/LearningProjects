package org.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Order(0)
public class Analitycs {

    @Pointcut("execution(* org.project.dao.*.*(..))")
    private void anyMethod() {}

    @Pointcut("execution(* org.project.dao.*.get*(..))")
    private void getter() {}

    @Pointcut("execution(* org.project.dao.*.set*(..))")
    private void setter() {}

    @Before("anyMethod() && !(getter())")
    public void beforeAny(JoinPoint joinPoint) {
        System.out.println("================>>> Executing @Before");
        Object[] args = joinPoint.getArgs();
        Arrays.stream(args).forEach(System.out::println);
        Signature signature = joinPoint.getSignature();
        System.out.println("Signature: " + signature);
        System.out.println("================>>> Log complete");
    }
}
