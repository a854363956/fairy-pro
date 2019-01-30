package com.fairy.models.logic.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fairy.models.dto.jpa.FairyGroupRole;

public interface RoleGroupModelJpa extends JpaRepository<FairyGroupRole,Long> ,CrudRepository<FairyGroupRole,Long>  {
   @Query(value = "from FairyGroupRole where userId = :userId")
   List<FairyGroupRole> findByUserId(@Param("userId") Long userId);
   
   
}
