package com.fairy.models.logic.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fairy.models.dto.jpa.FairyBaseRole;

public interface BaseRoleModelJpa extends JpaRepository<FairyBaseRole,Long>  {

}
