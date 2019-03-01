package com.fairy.controllers.user;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.common.Session;
import com.fairy.models.dto.Page;
import com.fairy.models.dto.Page.Filter;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.logic.UserModel;
import com.fairy.models.logic.UserModel.RespSession;
import com.fairy.models.logic.jpa.BaseUserModelJpa;

@RestController
@RequestMapping(value ="/api/user", method=RequestMethod.POST )
public class UserController {
	
	@Autowired private UserModel userModel;
	
	@Autowired private Session session;
	
	@Autowired  private BaseUserModelJpa baseUserModelJpa;
	
	@RequestMapping("/login")
	public ResponseDto<RespSession> login(@RequestBody RequestDto<JSONObject> request,HttpServletRequest req) {
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
	@RequestMapping("/getCurrentUser")
	public ResponseDto<Map<String,Object>> getCurrentUser(@RequestBody RequestDto<JSONObject> request) {
		return ResponseDto.getSuccess(userModel.getCurrentUser(session.getCurrentUser(request).get().getId()));
	}
	@RequestMapping("/delUser")
	public  ResponseDto<String> delUser(@RequestBody RequestDto<JSONObject> request){
		Long userId = request.getData().getLong("userId");
		Integer currentType = session.getCurrentRole(request).get().getRoleType();
		userModel.delUser(userId, currentType);
		return ResponseDto.getSuccess();
	}
	
	@RequestMapping("/findUserByLoginName")
	public ResponseDto<List<FairyBaseUser>> findUserByLoginName(@RequestBody RequestDto<JSONObject> request){
		String loginName = request.getData().getString("loginName");
		return ResponseDto.getSuccess(baseUserModelJpa.queryUserByLoginName(loginName));
	}
	
	@RequestMapping("/findUserByrealName")
	public ResponseDto<List<FairyBaseUser>> findUserByRealName(@RequestBody RequestDto<JSONObject> request){
		String realName = request.getData().getString("realName");
		return ResponseDto.getSuccess(baseUserModelJpa.queryUserByRealName(realName));
	}
	
	
	@RequestMapping("/findUserAll")
	public ResponseDto<Page<List<Map<String, Object>>>> findUserAll(@RequestBody RequestDto<Page<?>> request){
	   String roleName = "";
	   String email = "";
	   String loginName = "";
	   List<Filter> filters = request.getData().getFilters();
	   for (int i = 0; i < filters.size(); i++) {
		Filter filter = filters.get(i);
		switch (filter.getName()) {
			case "roleName":
				roleName = filter.getValue();
				break;
			case "email":
				email = filter.getValue();
				break;
			case "loginName":
				loginName = filter.getValue();
				break;
			default:
				break;
			}
		}
       
		return Page.toReturnPage(
				userModel.findUserInfoPage(roleName,email,loginName,
						PageRequest.of(
							request.getData().getPageNo() - 1, 
							request.getData().getPageSize()
					    )
				     )
				);
	}
	@RequestMapping("/addUser")
	public ResponseDto<String> addUser(@RequestBody RequestDto<JSONObject> request) {
		String loginName = request.getData().getString("loginName");
		String realName = request.getData().getString("realName");
		String identityCard = request.getData().getString("identityCard");
		String email = request.getData().getString("email");
		Long roleId = request.getData().getLong("roleId");
		Integer onlineTime = request.getData().getInteger("onlineTime");
		Long currentUser = session.getCurrentUser(request).get().getId();
		userModel.addUser(loginName, realName, identityCard, email, currentUser, roleId,onlineTime);
		return ResponseDto.getSuccess();
	}
	
	@RequestMapping("/updateUser")
	public ResponseDto<String> updateUser(@RequestBody RequestDto<JSONObject> request){
		String realName = request.getData().getString("realName");
		String identityCard = request.getData().getString("identityCard");
		String email = request.getData().getString("email");
		Long id = request.getData().getLong("id");
		Integer onlineTime = request.getData().getInteger("onlineTime");
		userModel.updateUser(id, realName, identityCard, email, onlineTime);
		return ResponseDto.getSuccess();
	}
}
