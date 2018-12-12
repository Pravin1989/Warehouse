package com.amiablecore.warehouse.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Pravin
 *
 */
@Component
@Aspect
public class WarehouseDAOAroundAdvice {
	private static Logger logger = LoggerFactory.getLogger(WarehouseDAOAroundAdvice.class);

	@AfterThrowing(pointcut = "execution (* com.amiablecore.warehouse.dao.impl.* .*(..))", throwing = "exception")
	public void afterThrowingException(JoinPoint joinPoint, Throwable exception) {
		logger.error("Exception Occured : {}", exception);
		logger.error("Arguments : {}", Arrays.toString(joinPoint.getArgs()));
		logger.error("Method Signature: {}", joinPoint.getSignature());
	}
}
