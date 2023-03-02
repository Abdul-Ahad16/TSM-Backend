package com.stg.tsm.exception;

public class TsmException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TsmException(String message) {
		this.message = message;
	}
}
