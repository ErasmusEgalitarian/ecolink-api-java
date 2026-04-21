package com.ecolink.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="API response wrapper")
public class APIResponse {
	@Schema(description="Tells if the request was successful")
	private boolean success;
	@Schema(description="Response data")
	private Object data;
	@Schema(description="Extra message")
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
	public void setSuccess(boolean success) {
    this.success = success;
	}

public void setData(Object data) {
    this.data = data;
	}

public void setMessage(String message) {
    this.message = message;
	}
}
