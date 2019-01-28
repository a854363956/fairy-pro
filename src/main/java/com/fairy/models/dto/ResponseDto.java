package com.fairy.models.dto;

import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
	private static final long serialVersionUID = 2384830540373738492L;
	private Integer status;
	private String  message;
	private T data;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
