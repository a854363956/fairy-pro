package com.fairy.models.logic.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fairy.models.dto.jpa.FairyBaseUser;

public interface UserModelJpa extends JpaRepository<FairyBaseUser,Long> ,CrudRepository<FairyBaseUser,Long>{
	@Query("from FairyBaseUser where loginName =:loginName")
	List<FairyBaseUser> findUserByLoginName(@Param("loginName") String loginName);
}
