package com.sintra.loanapp.exceptions;

import org.springframework.http.HttpStatus;

import java.util.NoSuchElementException;

import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ResponseEcxeptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleNotFoundCustomEx(EntityNotFoundException ex) {
		ExceptionApi exceptionApi = new ExceptionApi(HttpStatus.NOT_FOUND, "Requested Resource not found", ex);
		return new ResponseEntity<Object>(exceptionApi, exceptionApi.getStatus());
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleInvalidInput(MethodArgumentNotValidException ex) {
		BindingResult br = ex.getBindingResult();
		ExceptionApi exceptionApi = new ExceptionApi(HttpStatus.BAD_REQUEST, "Invalid input", ex);
		exceptionApi.addValidationErrors(br);
		return new ResponseEntity<Object>(exceptionApi, exceptionApi.getStatus());
	}

}
