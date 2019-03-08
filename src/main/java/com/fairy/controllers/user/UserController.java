package com.fairy.controllers.user;


import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.common.Session;
import com.fairy.models.dto.Page;
import com.fairy.models.dto.Page.Filter;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.dto.jpa.FairyBaseRole;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.dto.select.SelectGroup;
import com.fairy.models.dto.tree.Tree;
import com.fairy.models.exception.FairyException;
import com.fairy.models.logic.UserModel;
import com.fairy.models.logic.UserModel.RespSession;
import com.fairy.models.logic.jpa.BaseUserModelJpa;

@RestController
@RequestMapping(value ="/api/user")
public class UserController {
	
	@Autowired private UserModel userModel;
	
	@Autowired private Session session;
	
	@Autowired private BaseUserModelJpa baseUserModelJpa;
	
	
	@PostMapping("/login")
	public ResponseDto<RespSession> login(@RequestBody RequestDto<JSONObject> request,HttpServletRequest req) {
		JSONObject datas = request.getData();
		String loginName = datas.getString("loginName");
		String password = datas.getString("password");
		Integer equipment = datas.getInteger("equipment");
		return ResponseDto.getSuccess(
				userModel.login(loginName, password, req.getRemoteAddr(), equipment)
		);
	}
	
	@PostMapping("/logout")
	public ResponseDto<String> logout(@RequestBody RequestDto<JSONObject> request) {
		String token = request.getToken();
		userModel.logout(token);
		return ResponseDto.getSuccess();
	}
	
	@PostMapping("/getCurrentUser")
	public ResponseDto<Map<String,Object>> getCurrentUser(@RequestBody RequestDto<JSONObject> request) throws FairyException {
		Optional<FairyBaseUser> opt = session.getCurrentUser(request);
		if(opt.isPresent()) {
			return ResponseDto.getSuccess(userModel.getCurrentUser(opt.get().getId()));
		}else {
			throw new FairyException("Current personnel do not exist");
		}
	}
	
	@PostMapping("/delUser")
	public  ResponseDto<String> delUser(@RequestBody RequestDto<JSONObject> request) throws FairyException{
		Long userId = request.getData().getLong("userId");
		Optional<FairyBaseRole> opt = session.getCurrentRole(request);
		if(opt.isPresent()) {
			Integer currentType = opt.get().getRoleType();
			userModel.delUser(userId, currentType);
			return ResponseDto.getSuccess();
		}else {
			throw new FairyException("Current personnel do not exist");
		}
	}
	
	@PostMapping("/findUserByLoginName")
	public ResponseDto<List<FairyBaseUser>> findUserByLoginName(@RequestBody RequestDto<JSONObject> request){
		String loginName = request.getData().getString("loginName");
		return ResponseDto.getSuccess(baseUserModelJpa.queryUserByLoginName(loginName));
	}
	
	@PostMapping("/findUserByrealName")
	public ResponseDto<List<FairyBaseUser>> findUserByRealName(@RequestBody RequestDto<JSONObject> request){
		String realName = request.getData().getString("realName");
		return ResponseDto.getSuccess(baseUserModelJpa.queryUserByRealName(realName));
	}
	
	
	@PostMapping("/findUserAll")
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
	@PostMapping("/addUser")
	public ResponseDto<String> addUser(@RequestBody RequestDto<JSONObject> request) throws FairyException {
		String loginName = request.getData().getString("loginName");
		String realName = request.getData().getString("realName");
		String identityCard = request.getData().getString("identityCard");
		String email = request.getData().getString("email");
		Long roleId = request.getData().getLong("roleId");
		Integer onlineTime = request.getData().getInteger("onlineTime");
		Optional<FairyBaseUser> currentUser = session.getCurrentUser(request);
		if(currentUser.isPresent()) {
			userModel.addUser(loginName, realName, identityCard, email, currentUser.get().getId(), roleId,onlineTime);
			return ResponseDto.getSuccess();
		}else {
			throw new FairyException("Current personnel do not exist");
		}
	}
	
	@PostMapping("/findGroupRoleSelect")
	public ResponseDto<List<SelectGroup>> findSelectRole(@RequestBody RequestDto<JSONObject> request){
		return ResponseDto.getSuccess(userModel.findGroupRoleSelect());
	}
	
	@PostMapping("/findRoleTree")
	public ResponseDto<Tree> findRoleTree(@RequestBody RequestDto<JSONObject> request){
		return ResponseDto.getSuccess(userModel.findRoleTreeData());
	}
	
	@PostMapping("/updateUser")
	public ResponseDto<String> updateUser(@RequestBody RequestDto<JSONObject> request){
		String realName = request.getData().getString("realName");
		String identityCard = request.getData().getString("identityCard");
		String email = request.getData().getString("email");
		Long id = request.getData().getLong("id");
		Long roleName = request.getData().getLong("roleName");
		Integer onlineTime = request.getData().getInteger("onlineTime");
		userModel.updateUser(id, realName, identityCard, email, onlineTime,roleName);
		return ResponseDto.getSuccess();
	}
}
