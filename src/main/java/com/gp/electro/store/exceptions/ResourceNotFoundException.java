package com.gp.electro.store.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Resource not available");
	}
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
