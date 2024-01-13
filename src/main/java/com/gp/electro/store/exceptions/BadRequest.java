package com.gp.electro.store.exceptions;

public class BadRequest extends RuntimeException {

	public BadRequest() {

		super("Bad Request not valid extention");
	}

	public BadRequest(String message) {

		super(message);
	}

}
