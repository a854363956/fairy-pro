package com.fairy.controllers.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.dto.RequestDto;

@RestController
@RequestMapping(value ="/api/user", method=RequestMethod.POST )
public class UserController {
	
	@RequestMapping("/login")
	public void login(RequestDto<JSONObject> request) {
	}
	
	@RequestMapping("/logout")
	public void logout(RequestDto<JSONObject> request) {
	}
	@RequestMapping("/queryCurrentUserInfo")
	public void queryCurrentUserInfo(RequestDto<JSONObject> request) {
	}
	@RequestMapping("/register")
	public void register(RequestDto<JSONObject> request) {
	}
}
