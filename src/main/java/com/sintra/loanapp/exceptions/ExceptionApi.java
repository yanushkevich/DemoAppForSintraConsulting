package com.sintra.loanapp.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ExceptionApi {

	private HttpStatus status;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<String> errors = new ArrayList<>();

	private ExceptionApi() {
	        timestamp = LocalDateTime.now();
	    }

	ExceptionApi(HttpStatus status) {
	        this();
	        this.status = status;
	    }

	ExceptionApi(HttpStatus status, Throwable ex) {
	        this();
	        this.status = status;
	        this.message = "Unexpected error";
	      
	    }

	ExceptionApi(HttpStatus status, String message, Throwable ex) {
	        this();
	        this.status = status;
	        this.message = message;
	     
	    }
	
	//TODO this method need to be updated, return list of ErrorObjects 
	//instead of errors, where it will be possible to see all validation errors
    void addValidationErrors(Errors error) {
        error.getAllErrors().forEach(e -> this.errors.add(e.getDefaultMessage()));
    }

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
    
    
}
