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
@Table(name = "fairy_grant_route" )
public @Data class FairyGrantRoute implements Serializable {
	private static final long serialVersionUID = -6969298173942348746L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "role_id")
	private Long roleId;
	@Column(name = "route_id")
	private Long routeId;
	@Column(name = "authorize")
	private Long authorize;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "create_time")
	private Date createTime;
}
