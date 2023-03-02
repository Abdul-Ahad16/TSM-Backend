package com.stg.tsm.exception;

public class MobileGlobalException {
	
	private Object data;
	private String message;
	private int status;
	
	public MobileGlobalException(Object data, String message, int status) {
		super();
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public MobileGlobalException() {
		super();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
