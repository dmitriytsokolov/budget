package com.dmts.budget.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

@Aspect
@Configuration
public class TransactionAspect {

    @Around("execution(* com.dmts.budget.controller.MainController.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public String around(ProceedingJoinPoint joinPoint) throws Throwable {
        String result;
        System.out.println("start of transaction");
        result = (String) joinPoint.proceed();
        System.out.println("end of transaction");
        return result;
    }
}
