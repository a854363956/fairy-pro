package com.fairy.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fairy.models.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	protected Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseDto<Object> jsonErrorHandler(HttpServletRequest req, Exception e)  {
		logger.error("GlobalExceptionHandler",e);
		ResponseDto<Object> r = new ResponseDto<>();
		r.setMessage(e.getMessage());
		r.setStatus(500);
		return r;
	}
}
