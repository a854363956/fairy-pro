package com.fairy.models.logic.jpa;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fairy.models.dto.jpa.FairyBaseUser;

public interface BaseUserModelJpa extends JpaRepository<FairyBaseUser,Long> ,CrudRepository<FairyBaseUser,Long>{
	@Query("from FairyBaseUser where loginName =:loginName")
	List<FairyBaseUser> findUserByLoginName(@Param("loginName") String loginName);
	
	@Query("select "
			+ "new map("
			+ "u.loginName as loginName,"
			+ "u.realName as realName,"
			+ "u.identityCard as identityCard,"
			+ "br.roleName as roleName,"
			+ "u.email as email,"
			+ "u.onlineTime as onlineTime"
			+ ") "
			+ "from FairyBaseUser u "
			+ "left join FairyGrantRole r "
			+ "on r.userId = u.id "
			+ "left join FairyBaseRole br "
			+ "on br.id = r.roleId where u.id=:userId")
	Optional<Map<String,Object>> findUserInfo(@Param("userId") Long userId);
	
	
	@Query("select "
			+ "new map("
			+ "u.id as id,"
			+ "u.loginName as loginName,"
			+ "u.realName as realName,"
			+ "u.identityCard as identityCard,"
			+ "br.roleName as roleName,"
			+ "u.email as email,"
			+ "u.onlineTime as onlineTime"
			+ ") "
			+ "from FairyBaseUser u "
			+ "left join FairyGrantRole r "
			+ "on r.userId = u.id "
			+ "left join FairyBaseRole br "
			+ "on br.id = r.roleId where u.realName like %?1% and u.email like %?2% and u.loginName like %?3%"
			)
	Page<Map<String,Object>> findUserInfoPage(String roleName,String identityCard,String loginName,Pageable pageable);
}
