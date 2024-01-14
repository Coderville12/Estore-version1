package com.gp.electro.store.exceptions;

public class BadRequest extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequest() {

		super("Bad Request not valid extention");
	}

	public BadRequest(String message) {

		super(message);
	}

}
