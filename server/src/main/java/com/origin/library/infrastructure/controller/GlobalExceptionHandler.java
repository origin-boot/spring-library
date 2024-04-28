package com.origin.library.infrastructure.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.origin.library.domain.error.Error;
import com.origin.library.domain.error.InternalServerError;
import com.origin.library.domain.error.InvalidParameterError;
import com.origin.library.domain.error.RequestMethodError;
import com.origin.library.domain.error.RequestPathError;
import com.origin.library.infrastructure.utils.ExceptionUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

	final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleException(Exception ex) {

		logger.warning(ExceptionUtils.getStackTrace(ex, true, 1000));

		if (ex instanceof Error) {
			Error e = (Error) ex;
			return ResponseEntity.status(e.getStatus()).body(e);
		}

		if (ex instanceof MethodArgumentNotValidException) {
			Error e = new InvalidParameterError().setDetails(ex.getMessage());
			return ResponseEntity.status(e.getStatus()).body(e);
		}

		if (ex instanceof NoResourceFoundException) {
			Error e = new RequestPathError().setDetails(ex.getMessage());
			return ResponseEntity.status(e.getStatus()).body(e);
		}

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			Error e = new RequestMethodError().setDetails(ex.getMessage());
			return ResponseEntity.status(e.getStatus()).body(e);
		}

		Error e = new InternalServerError();
		return ResponseEntity.status(e.getStatus()).body(e);
	}
}