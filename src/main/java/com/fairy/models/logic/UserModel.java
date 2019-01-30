package com.fairy.models.logic;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fairy.models.common.Md5Variant;
import com.fairy.models.common.SnowflakeIdGenerator;
import com.fairy.models.dto.jpa.FairyBaseRole;
import com.fairy.models.dto.jpa.FairyBaseSession;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.dto.jpa.FairyGroupRole;
import com.fairy.models.logic.jpa.RoleGroupModelJpa;
import com.fairy.models.logic.jpa.RoleModelJpa;
import com.fairy.models.logic.jpa.SessionModelJpa;
import com.fairy.models.logic.jpa.UserModelJpa;
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
	@Autowired
	private RoleModelJpa roleModelJap;
	/**
	 * 验证当前用户的密码是否正确
	 * @param loginName 当前登入的账号
	 * @param password   当前的密码
	 */
	public UserVerifyStatus verify(List<FairyBaseUser> users,String loginName,String password) {
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
	 *   退出登入,删除对应的会话信息
	 * @param sessionCode
	 */
	@Transactional
	public void logout(String sessionCode) {
		sessionModelJpa.deleteBySessionCode(sessionCode);
	}
	
	/**
	 *  添加用户
	 * @param loginName    登入名称
	 * @param realName     用户的真实姓名
	 * @param identityCard 身份证
	 * @param password     用户密码
	 * @param email        用户的邮箱地址
	 * @param roleId       所属的角色ID
	 * @param currentType  当前登入人的角色类别
	 * @param currentUser  当前登入人的人员ID
	 */
	@Transactional
	public void addUser(
				String loginName,
				String realName,
				String identityCard,
				String password,
				String email,
				Integer currentType,
				Long  currentUser,
				Long roleId
			) {
		Optional<FairyBaseRole> roleInfo = roleModelJap.findById(roleId);
		// 只有类型为1 或者类型为0 的才能进行创建人员,否则表示权限不足
		if(1 != currentType && 0 != currentType) {
			throw new RuntimeException("Permission Denied");
		}
		if(roleInfo.isPresent()) {
			if(roleInfo.get().getRoleType() <= currentType) {
				throw new RuntimeException(String.format("Insufficient permissions, current permissions [ %s ], target permissions [ %s ].", currentType,roleInfo));
			}else {
				
				// 创建人员信息
				FairyBaseUser fbu = new FairyBaseUser();
				fbu.setCreateTime(new Date());
				fbu.setEmail(email);
				fbu.setId(snowflakeId.nextId());
				fbu.setIdentityCard(identityCard);
				fbu.setLoginName(loginName);
				fbu.setPassword(Md5Variant.strongEncryption(password));
				fbu.setRealName(realName);
				userModelJpa.save(fbu);
				
				// 创建角色关联信息
				FairyGroupRole fgr = new FairyGroupRole();
				fgr.setId(snowflakeId.nextId());
				fgr.setCreateTime(new Date());
				fgr.setAuthorize(currentUser);
				fgr.setRoleId(roleId);
				fgr.setUserId(fbu.getId());
				roleGroupModelJpa.save(fgr);
				
				return;
			}
		}else {
			throw new RuntimeException(String.format("Of course, the role does not exist. Please check it.[ %s ]", roleId));
		}
	}
	/**
	 *   用户登入
	 * @param loginName 登入名称
	 * @param password  登入的密码
	 * @param ipAddr    登入的IP地址
	 * @param equipment 登入的设备类型
	 * @return 如果登入成功,返回sessionCode
	 */
	public Map<String, Object> login(String loginName,String password,String ipAddr,Integer equipment) {
		List<FairyBaseUser> users =  userModelJpa.findUserByLoginName(loginName);
		UserVerifyStatus uvs = verify(users,loginName,password);
		switch (uvs) {
		case SUCCESS:
			FairyBaseSession fbs = new FairyBaseSession(
					snowflakeId.nextId(),
					users.get(0).getId(),
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
		case UNMAINTAINED_ROLES:
			return ImmutableMap.of(
					"status",UserVerifyStatus.USER_DOES_NOT_EXIST
					);
		default:
			return ImmutableMap.of(
					"status",uvs
					);
		}
	}
	
}
