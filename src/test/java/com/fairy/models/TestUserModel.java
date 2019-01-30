package com.fairy.models;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.fairy.models.logic.UserModel;
import com.fairy.models.logic.UserModel.UserVerifyStatus;
import com.fairy.models.logic.jpa.SessionModelJpa;
import com.fairy.models.logic.jpa.UserModelJpa;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserModel {
   @Autowired
   private UserModel userModel;
   @Autowired
   private UserModelJpa userModelJpa;
   @Autowired
   private SessionModelJpa sessionModelJpa;
   @Test
   public void testIsHaveUser() {
	   UserVerifyStatus result = userModel.verify("notHaveUser","1234");
	   assertEquals(result, UserVerifyStatus.USER_DOES_NOT_EXIST);
	   System.out.println(result);
   }
   
   @Test
   public void testLogin() {
	   int num = sessionModelJpa.findAll().size();
	   Map<String, Object> map =  userModel.login("admin", "admin", "127.0.0.1", 0);
	   assertEquals(UserVerifyStatus.SUCCESS, map.get("status") );
	   assertEquals(num+1,sessionModelJpa.findAll().size());
	   System.out.println(JSON.toJSONString(map));
   }
   
   @Test
   public void testAddUser() throws Exception {
		String loginName="zhangj";
		String realName="张尽";
		String identityCard="429005199609080071";
		String password="admin";
		String email="zhangjin0908@hotmail.com";
		Integer currentType=1;
		Long  currentUser= 0L;
		Long roleId=0L;
		userModel.addUser(loginName, realName, identityCard, password, email, currentType, currentUser, roleId);
		int size = userModelJpa.findUserByLoginName("zhangj").size();
		assertEquals(size, 1);
   }
   @Test
   public void testOLogot() {
	   sessionModelJpa.findAll().forEach((data)->{
		   userModel.logout(data.getSessionCode());
	   });
	   assertEquals(sessionModelJpa.findAll().size(), 0);
   }
}
