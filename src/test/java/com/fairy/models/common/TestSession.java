package com.fairy.models.common;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fairy.models.dto.jpa.FairyBaseRole;
import com.fairy.models.dto.jpa.FairyBaseSession;
import com.fairy.models.dto.jpa.FairyBaseUser;
import com.fairy.models.logic.jpa.SessionModelJpa;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSession {
	@Autowired
	private Session session ;
	
	@Autowired
	private SessionModelJpa sessionModelJpa;
	
	@Test
	public void testGetCurrentUser() {
		sessionModelJpa.findAll().forEach((data)->{
			Optional<FairyBaseUser> d = session.getCurrentUser(data.getSessionCode());
			assertEquals(d.isPresent(), true);
		});
	}
	
	@Test
	public void testGetCurrentRole() {
		sessionModelJpa.findAll().forEach((data)->{
			Optional<FairyBaseRole> d = session.getCurrentRole(data.getSessionCode());
			assertEquals(d.isPresent(), true);
		});
	}
}
