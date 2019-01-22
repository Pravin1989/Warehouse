package com.amiablecore.warehouse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Pravin
 *
 */
@ControllerAdvice
public class RestResponseExceptionHandling extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandling.class);

	public RestResponseExceptionHandling() {
		super();
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(DataIntegrityViolationException ex, WebRequest request) {
		final String reponseBody = ex.getMessage();
		logger.error(reponseBody, ex);
		return handleExceptionInternal(ex, reponseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String body = ex.getMessage();
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		final String body = ex.getMessage();
		return handleExceptionInternal(ex, body, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ java.nio.file.AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(final Exception exception, final WebRequest request) {
		return new ResponseEntity<>("Access Denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleRuntimeException(final Exception ex, final WebRequest request) {
		final String body = ex.getMessage();
		logger.error(body, ex.getCause().getCause());
		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
