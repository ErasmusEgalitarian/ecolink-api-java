package com.example.demo.dto;

public class APIResponse {

	private boolean success;
	private Object data;
	private String message;
	
	public APIResponse(boolean success, Object data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public Object getData() {
		return data;
	}
	public String getMessage() {
		return message;
	} 
}
