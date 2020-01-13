package com.skittles.buyticket.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {
    @Before(value="execution(* com.skittles.buyticket.controller.TestController.test1(..))")
   public void doBore(){

   }
}
