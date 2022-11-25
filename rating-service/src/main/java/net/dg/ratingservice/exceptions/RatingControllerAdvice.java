package net.dg.ratingservice.exceptions;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class RatingControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { FeignException.class, ValidationException.class })
	public ResponseEntity<Map<String, String>> globalExceptionHandler(Exception e) {
		e.printStackTrace();
		Map<String, String> message = Collections.singletonMap("message", e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ RetryableException.class })
	public ResponseEntity<String> retryException(RetryableException e) {
		String message = "Service unavailable at the moment, please try again later.";
		return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
	}

}