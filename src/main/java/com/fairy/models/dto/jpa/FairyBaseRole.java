package com.fairy.models.dto.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "fairy_base_role" )
public @Data class FairyBaseRole implements Serializable{
	
	private static final long serialVersionUID = 5039501984702120313L;
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "role_type")
	private Integer roleType;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "create_time")
	private Date createTime;
}
