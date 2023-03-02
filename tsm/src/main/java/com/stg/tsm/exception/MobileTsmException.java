package com.stg.tsm.exception;

public class MobileTsmException extends Exception {
	
	private Object data;
	private String message;
	public MobileTsmException(Object data, String message) {
		super();
		this.data = data;
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
