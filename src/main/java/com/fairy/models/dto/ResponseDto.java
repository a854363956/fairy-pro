package com.fairy.models.dto;

import java.io.Serializable;

import lombok.Data;

public @Data class ResponseDto<T> implements Serializable {
	private static final long serialVersionUID = 2384830540373738492L;
	private Integer status;
	private String message = "";
	private T data;

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
