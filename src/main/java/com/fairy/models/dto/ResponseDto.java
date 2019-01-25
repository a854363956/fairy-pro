package com.fairy.models.dto;

import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
	private static final long serialVersionUID = 2384830540373738492L;
	// 唯一ID
	private String id;
	// 状态码
	private Integer status;
	// 错误的消息信息 
	private String  message;
	// 返回的数据信息
	private T body;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
