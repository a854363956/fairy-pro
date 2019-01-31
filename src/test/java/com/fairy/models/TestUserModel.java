package com.fairy.models;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fairy.controllers.user.UserController;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.dto.jpa.FairyBaseSession;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.dto.jpa.FairyGroupRole;
import com.fairy.models.logic.UserModel;
import com.fairy.models.logic.UserModel.UserVerifyStatus;
import com.fairy.models.logic.jpa.RoleGroupModelJpa;
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
   @Autowired
   private UserController userController;
   @Autowired
   private RoleGroupModelJpa roleGroupModelJpa;
   @Test
   public void testIsHaveUser() {
	   String loginName = "notHaveUser";
	   List<FairyBaseUser> users =  userModelJpa.findUserByLoginName(loginName);
	   UserVerifyStatus result = userModel.verify(users,loginName,"1234");
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
	   FairyBaseSession fbs = sessionModelJpa.findBySessionCode(map.get("sessionCode").toString()).get();
	   sessionModelJpa.delete(fbs);
   }
   

   
   @Test
   public void testAddUser() throws Exception {
	   RequestDto<JSONObject> request = new RequestDto<JSONObject>();
	   
	   JSONObject json = new JSONObject();
	   json.put( "loginName","zhangj");
	   json.put( "realName","张尽");
	   json.put( "identityCard","429005199609080071");
	   json.put( "password","admin");
	   json.put( "roleId",1);
	   json.put( "email", "zhangjin0908@Hotmail.com");
	   
	   request.setData(json);
	   request.setToken("1ca441059b9f4d749d6937f94672dd034d70c280808b4ee48e22709bb813d9ff");
	   ResponseDto<String> resp = userController.addUser(request);
	   int status = resp.getStatus();
	   assertEquals(200,status);
	   
	   FairyBaseUser fbu = userModelJpa.findUserByLoginName("zhangj").get(0);
	   
	   FairyGroupRole fgr = roleGroupModelJpa.findByUserId(fbu.getId()).get(0);
       roleGroupModelJpa.delete(fgr);	   
	   userModelJpa.delete(fbu);
	   
   }
   @Test
   public void testOLogot() {
	   sessionModelJpa.findAll().forEach((data)->{
		   userModel.logout(data.getSessionCode());
	   });
	   assertEquals(sessionModelJpa.findAll().size(), 0);
   }
}
