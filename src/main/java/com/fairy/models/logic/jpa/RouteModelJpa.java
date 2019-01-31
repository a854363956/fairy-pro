package com.fairy.models.logic.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fairy.models.dto.jpa.FairyBaseRoute;

public interface RouteModelJpa extends JpaRepository<FairyBaseRoute,Long> ,CrudRepository<FairyBaseRoute,Long> {

}
