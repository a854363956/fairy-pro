package com.fairy.models.exception;

public class FairyException  extends Exception{
	private static final long serialVersionUID = 477747018454891457L;

	public FairyException() {
		super();
	}

	public FairyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FairyException(String message, Throwable cause) {
		super(message, cause);
	}

	public FairyException(String message) {
		super(message);
	}

	public FairyException(Throwable cause) {
		super(cause);
	}
	
}
