package com.sintra.loanapp.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Long resourceId;
	
	 public EntityNotFoundException() {
	       
	    }

	 
    public EntityNotFoundException(String message, Long resourceId) {
        super(message);
        this.resourceId = resourceId;
    }

}
