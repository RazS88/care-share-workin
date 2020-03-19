package com.raz.rest;

import org.springframework.http.HttpStatus;

public class UserErrorResponse {

	private HttpStatus status;
	private String message;
	private long timestamp;
	
	public UserErrorResponse(HttpStatus status, String message, long timestamp) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public static UserErrorResponse now(HttpStatus status , String message) {
		return new UserErrorResponse(status, message, System.currentTimeMillis());
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
	
	
}
