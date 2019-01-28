package com.fairy.models.logic.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.fairy.models.dto.jpa.FairyBaseSession;

public interface SessionModelJpa extends JpaRepository<FairyBaseSession,Long> ,CrudRepository<FairyBaseSession,Long> {
}
