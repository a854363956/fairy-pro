package com.fairy.models.logic;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fairy.models.common.Md5Variant;
import com.fairy.models.common.SnowflakeIdGenerator;
import com.fairy.models.common.StreamUtil;
import com.fairy.models.dto.jpa.FairyBaseRole;
import com.fairy.models.dto.jpa.FairyBaseSession;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.dto.jpa.FairyGrantRole;
import com.fairy.models.dto.select.SelectGroup;
import com.fairy.models.dto.tree.Tree;
import com.fairy.models.dto.tree.TreeNode;
import com.fairy.models.logic.jpa.GrantRoleModelJpa;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Lists;
import com.fairy.models.logic.jpa.BaseRoleModelJpa;
import com.fairy.models.logic.jpa.BaseSessionModelJpa;
import com.fairy.models.logic.jpa.BaseUserModelJpa;

import lombok.Data;


@Service
public class UserModel {
	@Value("${fairy.defaultPassword}") String defaultPassword;
	public enum UserVerifyStatus{
		SUCCESS, // 成功
		WRONG_PASSWORD, // 密码错误
		USER_DOES_NOT_EXIST, // 用户不存在
		SYSTEM_ERROR, // 系统错误
		UNMAINTAINED_ROLES,// 人员未维护角色
	}
	@Autowired
	private BaseUserModelJpa userModelJpa;
	@Autowired
	private SnowflakeIdGenerator snowflakeId;
	@Autowired
	private BaseSessionModelJpa sessionModelJpa;
	@Autowired
	private GrantRoleModelJpa roleGroupModelJpa;
	@Autowired
	private BaseRoleModelJpa roleModelJap;
	@Autowired
	private GrantRoleModelJpa grantRoleModelJpa;
	
	private Map<Integer,String> roleTypeMapping=ImmutableBiMap.of(
			0, "ROOT角色", 
			1, "管理角色", 
			2, "普通角色");
	@Transactional
	public void updateUser(Long userId,String realName,String identityCard,String email,Integer onlineTime,Long roleId) {
		userModelJpa.updateUser(userId, realName, identityCard, email, onlineTime);
		
		List<FairyGrantRole> roles = grantRoleModelJpa.findByUserId(userId);
		roles.forEach((data)->{
			grantRoleModelJpa.delete(data);
		});
		
		FairyGrantRole fgr = new FairyGrantRole();
		fgr.setAuthorize(userId);
		fgr.setCreateTime(new Date());
		fgr.setRoleId(roleId);
		fgr.setUserId(userId);
		fgr.setId(snowflakeId.nextId());
		
		grantRoleModelJpa.save(fgr);
	}
	
	/**
	 *  获取角色信息 - 不支持嵌套
	 * @return
	 */
	public Tree findRoleTreeData() {
		List<FairyBaseRole> fbr = roleModelJap.findAll();
		Tree tree = new Tree();
		tree.setTreeNode(Lists.newArrayList());
		for(FairyBaseRole role : fbr) {
			TreeNode treeNode = new TreeNode();
			treeNode.setKey(role.getId());
			treeNode.setTitle(role.getRoleName());
			tree.getTreeNode().add(treeNode);
		}
		return tree;
	}
	
	/**
	 * 返回人员信息的GourpSelect的数据
	 * @return
	 */
	public List<SelectGroup> findGroupRoleSelect() {
		List<FairyBaseRole> fbr = roleModelJap.findAll();
		List<FairyBaseRole> groupFbr = fbr.stream()
				.filter(StreamUtil.distinctByKey(FairyBaseRole::getRoleType))
				.collect(Collectors.toList());
		List<SelectGroup> result = Lists.newArrayList();
		for(FairyBaseRole f : groupFbr) {
			Integer roleType = f.getRoleType();
			SelectGroup selectGroup= new SelectGroup();
			selectGroup.setGroupName(roleTypeMapping.get(roleType));
			selectGroup.setGroupList(Lists.newArrayList());
			fbr.stream()
			.filter(data -> data.getRoleType().equals(f.getRoleType()))
			.collect(Collectors.toList()).forEach((data)->{
				com.fairy.models.dto.select.Select select = new com.fairy.models.dto.select.Select();
				select.setKey(data.getRoleName());
				select.setValue(""+data.getId());
				selectGroup.getGroupList().add(select);
			});
			
			result.add(selectGroup);
		}
		return result;
		
	}

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
	public Map<String,Object> getCurrentUser(Long userId) {
		return userModelJpa.findUserInfo(userId).get();
	}
	
	public Page<Map<String, Object>> findUserInfoPage(String roleName,String email,String loginName,Pageable pageable) {
		return userModelJpa.findUserInfoPage(roleName,email,loginName, pageable);
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
	 * @param email        用户的邮箱地址
	 * @param roleId       所属的角色ID
	 * @param currentType  当前登入人的角色类别
	 * @param currentUser  当前登入人的人员ID
	 * @param onlineTime   在线时长/分钟
	 */
	@Transactional
	public void addUser(
				String loginName,
				String realName,
				String identityCard,
				String email,
				Long  currentUser,
				Long roleId,
				Integer onlineTime
			) {
		
		Optional<FairyBaseRole> roleInfo = roleModelJap.findById(roleId);
		if(roleInfo.isPresent()) {
			// 创建人员信息
			FairyBaseUser fbu = new FairyBaseUser();
			fbu.setCreateTime(new Date());
			fbu.setEmail(email);
			fbu.setId(snowflakeId.nextId());
			fbu.setIdentityCard(identityCard);
			fbu.setLoginName(loginName);
			fbu.setPassword(Md5Variant.strongEncryption(defaultPassword));
			fbu.setRealName(realName);
			fbu.setOnlineTime(onlineTime);
			userModelJpa.save(fbu);
			
			// 创建角色关联信息
			FairyGrantRole fgr = new FairyGrantRole();
			fgr.setId(snowflakeId.nextId());
			fgr.setCreateTime(new Date());
			fgr.setAuthorize(currentUser);
			fgr.setRoleId(roleId);
			fgr.setUserId(fbu.getId());
			roleGroupModelJpa.save(fgr);
			return;
		}else {
			throw new RuntimeException(String.format("Of course, the role does not exist. Please check it.[ %s ]", roleId));
		}
	}
	public @Data static class RespSession{
		private String sessionCode;
		private UserVerifyStatus status;
		public RespSession(String sessionCode, UserVerifyStatus status) {
			super();
			this.sessionCode = sessionCode;
			this.status = status;
		}
		
	}
	
	public void delUser(Long userId,Integer currentRoleType) {
		Optional<FairyBaseUser> user = userModelJpa.findById(userId);
		if(user.isPresent()) {
			Optional<FairyBaseSession> sess = sessionModelJpa.findByUserId(userId);
			if(sess.isPresent()) {
				throw new RuntimeException("The user is unable to delete the operation, please wait for the user to quit");
			}else {
				List<FairyGrantRole> roleGrant = roleGroupModelJpa.findByUserId(userId);
				roleGrant.forEach((data)->{
					roleGroupModelJpa.delete(data);
				});
				
				userModelJpa.delete(user.get());
			}
		}else {
			throw new RuntimeException(String.format("User id does not exist. [ %s ]", userId));
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
	public RespSession login(String loginName,String password,String ipAddr,Integer equipment) {
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
			return new RespSession(
						fbs.getSessionCode(),
						UserVerifyStatus.SUCCESS
					);
		case WRONG_PASSWORD:
			return new RespSession(
					"",UserVerifyStatus.WRONG_PASSWORD
					);
		case USER_DOES_NOT_EXIST:
			return new RespSession(
					"",UserVerifyStatus.USER_DOES_NOT_EXIST
					);
		case UNMAINTAINED_ROLES:
			return new RespSession(
					"",UserVerifyStatus.USER_DOES_NOT_EXIST
					);
		default:
			return new RespSession(
					"",uvs
					);
		}
	}
	
}
