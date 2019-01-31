package com.fairy.models.dto;

import java.io.Serializable;

import lombok.Data;


public @Data class RequestDto<T> implements Serializable {
	private static final long serialVersionUID = 3588343400070835840L;
	private String token;
	private T data ;
}
