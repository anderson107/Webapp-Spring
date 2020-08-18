package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.sun.istack.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger myLogger = 
			Logger.getLogger(getClass().getName(), CRMLoggingAspect.class);
	
	// setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	// do the same for service and dao
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage()||forServicePackage()||forDaoPackage()")
	private void forAppFlow(){}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		// display method we are calling
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("**** in @Before: calling method " + method);
		
		// display the arguments to the method
		
		// get the arguments
		Object[]args = joinPoint.getArgs();
		
		// loop and display arguments
		for(Object o: args) {
			myLogger.info("===========> argument" + o);
		}
	}
	
	// add @After advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="result"
			)
	public void after(JoinPoint joinPoint, Object result) {
		
		// display method we are returning from
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("**** in @After: from method " + method);
		
		// display data returned
		myLogger.info("===============> result " + result);
	}
}
