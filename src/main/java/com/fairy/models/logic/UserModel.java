package com.fairy.models.logic;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.fairy.models.common.Md5Variant;
import com.fairy.models.common.SnowflakeIdGenerator;
import com.fairy.models.dto.jpa.FairyBaseSession;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.logic.jpa.RoleGroupModelJpa;
import com.fairy.models.logic.jpa.SessionModelJpa;
import com.fairy.models.logic.jpa.UserModelJpa;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;


@Service
public class UserModel {
	public enum UserVerifyStatus{
		SUCCESS,
		WRONG_PASSWORD,
		USER_DOES_NOT_EXIST,
		SYSTEM_ERROR,
		UNMAINTAINED_ROLES,
	}
	@Autowired
	private UserModelJpa userModelJpa;
	@Autowired
	private SnowflakeIdGenerator snowflakeId;
	@Autowired
	private SessionModelJpa sessionModelJpa;
	@Autowired
	private RoleGroupModelJpa roleGroupModelJpa;
	/**
	 * 验证当前用户的密码是否正确
	 * @param loginName 当前登入的账号
	 * @param password   当前的密码
	 */
	public UserVerifyStatus verify(String loginName,String password) {
		List<FairyBaseUser> users =  userModelJpa.findUserByLoginName(loginName);
		if(users.size() == 0) {
			return UserVerifyStatus.USER_DOES_NOT_EXIST;
		}else {
			if(roleGroupModelJpa.findByUserId(users.get(0).getId()).size() == 0) {
				return UserVerifyStatus.UNMAINTAINED_ROLES;
			}
			
			if(users.get(0).getPassword().equals(Md5Variant.strongEncryption(password))) {
				return UserVerifyStatus.SUCCESS;
			}else {
				return UserVerifyStatus.WRONG_PASSWORD;
			}
		}
	}
	
	/**
	 * 用户登入
	 * @param loginName 登入名称
	 * @param password  登入的密码
	 * @param ipAddr    登入的IP地址
	 * @param equipment 登入的设备类型
	 * @return 如果登入成功,返回sessionCode
	 */
	public Map<String, Object> login(String loginName,String password,String ipAddr,Integer equipment) {
		switch (verify(loginName,password)) {
		case SUCCESS:
			FairyBaseSession fbs = new FairyBaseSession(
					snowflakeId.nextId(),
					ipAddr,
					equipment
					);
			sessionModelJpa.save(fbs);
			return ImmutableMap.of(
					"sessionCode",fbs.getSessionCode(),
					"status",UserVerifyStatus.SUCCESS
					);
		case WRONG_PASSWORD:
			return ImmutableMap.of(
					"status",UserVerifyStatus.WRONG_PASSWORD
					);
		case USER_DOES_NOT_EXIST:
			return ImmutableMap.of(
					"status",UserVerifyStatus.USER_DOES_NOT_EXIST
					);
		default:
			return ImmutableMap.of(
					"status",UserVerifyStatus.SYSTEM_ERROR
					);
		}
	}
	
}
