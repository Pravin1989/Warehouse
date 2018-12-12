package com.amiablecore.warehouse.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAdvice {
	private static Logger logger;

	/**
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
	public Object logBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		logger = LoggerFactory.getLogger(proceedingJoinPoint.getClass());
		logger.info("Started : {}", proceedingJoinPoint.getSignature());
		logger.info("Arguments : {}", Arrays.toString(proceedingJoinPoint.getArgs()));
		return proceedingJoinPoint.proceed();
	}

	/**
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.amiablecore.warehouse.controller.* .*(..)) || execution(* com.amiablecore.rest.warehouse.impl.* .*(..)) || execution(* com.amiablecore.warehouse.dao.impl.* .*(..))", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
		logger = LoggerFactory.getLogger(joinPoint.getClass());
		logger.info("Method Returned Value : {}", result);
		logger.info("End : {}", joinPoint.getSignature());
	}
}