package com.fairy.models.logic.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fairy.models.dto.jpa.FairyGrantRole;

public interface RouteGrantModelJpa extends JpaRepository<FairyGrantRole,Long> ,CrudRepository<FairyGrantRole,Long>{

}
