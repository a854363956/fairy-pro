package com.fairy.models.dto;

import java.io.Serializable;

import com.fairy.models.session.Session;

public class RequestDto<T> implements Serializable, Session {
	private static final long serialVersionUID = 3588343400070835840L;
	private String token;
	private T data;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
