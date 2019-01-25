package com.fairy.controllers.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.dto.RequestDto;

/**
  *    登入模块,用来进行用户登入,以及用户注册的相关模块
 */
@RestController
@RequestMapping(value ="/api/user", method=RequestMethod.POST )
public class UserController {
	
	/**
	 *  登入系统
	 * @param request
	 */
	@RequestMapping("/login")
	public void login(RequestDto<JSONObject> request) {
	}
	
	/**
	 * 登出系统
	 * @param request
	 */
	@RequestMapping("/logout")
	public void logout(RequestDto<JSONObject> request) {
	}
	
	/**
	 * 查询当前用户信息
	 * @param request
	 */
	@RequestMapping("/queryCurrentUserInfo")
	public void queryCurrentUserInfo(RequestDto<JSONObject> request) {
	}
	
	/**
	 *  用户注册
	 * @param request
	 */
	@RequestMapping("/register")
	public void register(RequestDto<JSONObject> request) {
	}
}
