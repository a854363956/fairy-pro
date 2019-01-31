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
@Table(name = "fairy_grant_role" )
public @Data class FairyGrantRole implements Serializable {
	private static final long serialVersionUID = -950862400013919633L;
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "authorize")
	private Long authorize;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "create_time")
	private Date createTime;
	
}
