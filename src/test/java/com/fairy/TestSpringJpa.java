package com.fairy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.fairy.models.common.SnowflakeIdGenerator;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.logic.jpa.UserModelJpa;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringJpa {
	@Autowired
	private UserModelJpa userModelJpa;
	@Test
	public void testQuery() {
		List<FairyBaseUser> query = userModelJpa.findAll();
		int size = query.size();
		assertNotEquals(size, -1);
	}
	@Autowired
	private SnowflakeIdGenerator snowflakeId;
	@Test
	public void testCreateUser() {
		FairyBaseUser user = new FairyBaseUser();
		user.setCreateTime(new Date());
		user.setEmail("zhangjin0908@hotmail.com");
		user.setIdentityCard("429005199609080071");
		user.setLoginName("zhangj");
		user.setPassword("zhangj");
		user.setRealName("张尽");
		user.setId(snowflakeId.nextId());
		userModelJpa.save(user);
		
		assertEquals(userModelJpa.findUserByLoginName("zhangj").size(),1);
		
		System.out.println(JSON.toJSONString(userModelJpa.findUserByLoginName("zhangj").get(0)));
		userModelJpa.delete(user);
	}
	
}
