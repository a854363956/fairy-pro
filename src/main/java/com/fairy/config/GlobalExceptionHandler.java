package com.fairy.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fairy.models.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseDto<Object> jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		ResponseDto<Object> r = new ResponseDto<>();
		r.setMessage(e.getMessage());
		r.setStatus(500);
		return r;
	}
}
