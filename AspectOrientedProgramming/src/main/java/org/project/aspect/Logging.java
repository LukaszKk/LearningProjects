package org.project.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

    @Before("execution(public void org.project.dao.Account.addAccount())")
    public void beforeAddAccountAdvice() {
        System.out.println("================>>> Executing @Before on add account");
    }

    @After("execution(* update*())")
    public void afterUpdateAdvice() {
        System.out.println("================>>> Executing @After on update method");
    }

    @After("execution(* add*())")
    public void afterAddAdvice() {
        System.out.println("================>>> Executing @After on add method");
    }
}
