package com.fairy.models.dto;

import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
	private static final long serialVersionUID = 2384830540373738492L;
	private Integer status;
	private String message = "";
	private T data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
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
	public static ResponseDto<String> getSuccess(){
		ResponseDto<String> re = new ResponseDto<String>();
		re.setStatus(200);
		re.setData("Data operation completed.");
		return re;
	}
	public static ResponseDto<Object> getSuccess(Object object){
		ResponseDto<Object> re = new ResponseDto<Object>();
		re.setStatus(200);
		re.setData(object);
		return re;
	}
}
