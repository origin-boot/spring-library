package com.origin.library.domain;

public class Error extends Exception {
	public int code;
	public String message;
	public String details;
	protected int status;

	private final static int DEFAULT_STATUS = 500;

	public Error(int code, String message) {
		this.code = code;
		this.message = message;
		this.details = "";
		this.status = DEFAULT_STATUS;
	}

	public Error(int code, String message, String details) {
		this.code = code;
		this.message = message;
		this.details = details;
		this.status = DEFAULT_STATUS;
	}

	public int getStatus() {
		return status;
	}

	public Error setStatus(int status) {
		this.status = status;
		return this;
	}
}
