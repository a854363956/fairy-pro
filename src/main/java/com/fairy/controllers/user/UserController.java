package com.fairy.controllers.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.logic.UserModel;

@RestController
@RequestMapping(value ="/api/user", method=RequestMethod.POST )
public class UserController {
	@Autowired
	private UserModel userModel;
	
	@RequestMapping("/login")
	public ResponseDto<Object> login(@RequestBody RequestDto<JSONObject> request,HttpServletRequest req) {
		JSONObject datas = request.getData();
		String loginName = datas.getString("loginName");
		String password = datas.getString("password");
		Integer equipment = datas.getInteger("equipment");
		return ResponseDto.getSuccess(
				userModel.login(loginName, password, req.getRemoteAddr(), equipment)
		);
	}
	
	@RequestMapping("/logout")
	public ResponseDto<String> logout(@RequestBody RequestDto<JSONObject> request) {
		String token = request.getToken();
		userModel.logout(token);
		return ResponseDto.getSuccess();
	}
}
