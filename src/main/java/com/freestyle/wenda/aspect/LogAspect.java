package com.freestyle.wenda.aspect;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private Log log = LogFactory.getFactory().getInstance(LogAspect.class);

    @Before("execution(* com.freestyle.wenda.controller.*.*(..))")
    public void beforeMethod() {
        log.info("before method");
    }


    @After("execution(* com.freestyle.wenda.controller.*.*(..))")
    public void afterMethod() {
        log.info("after method");
    }
}
