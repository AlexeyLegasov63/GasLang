package org.gaslang.script.exception;

import java.io.Serial;

public class ValueMatchingTypeError extends RunError {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 252535L;

	public ValueMatchingTypeError() {
		// TODO Auto-generated constructor stub
	}

	public ValueMatchingTypeError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ValueMatchingTypeError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ValueMatchingTypeError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ValueMatchingTypeError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
