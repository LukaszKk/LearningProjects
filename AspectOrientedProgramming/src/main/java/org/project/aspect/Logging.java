package org.project.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class Logging {

    @Before("execution(public void org.project.dao.Account.addAccount())")
    public void beforeAddAccountAdvice() {
        System.out.println("================>>> Executing @Before on add account");
    }

    @After("execution(* update*())")
    public void afterUpdateAdvice() {
        System.out.println("================>>> Executing @After on update method");
    }

    @Pointcut("execution(* org.project.dao.*.add*(..))")
    private void afterAdd() {}

    @After("afterAdd()")
    public void afterAddAdvice() {
        System.out.println("================>>> Executing @After on add method");
    }
}
