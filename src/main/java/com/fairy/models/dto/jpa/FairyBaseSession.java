package com.fairy.models.dto.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "fairy_base_user" )
public class FairyBaseSession implements Serializable{
	private static final long serialVersionUID = 647410867485886904L;
	@Id
	@Column(name="id")
	private Long id;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name="last_time")
	private Date lastTime;
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name="create_time")
	private Date createTime;
	@Column(name="sessionCode")
	private String sessionCode;
	@Column(name="ipAddr")
	private String ipAddr;
	@Column(name="equipment")
	private Integer equipment;
	
	public FairyBaseSession(
			Long id,
			String ipAddr, 
			Integer equipment) {
		super();
		this.ipAddr = ipAddr;
		this.equipment = equipment;
		this.id = id;
		this.sessionCode = (UUID.randomUUID().toString()+UUID.randomUUID().toString()).replaceAll("-", "");
		this.createTime=new Date();
		this.lastTime = new Date();
	}
	
	public FairyBaseSession() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getCreate_time() {
		return createTime;
	}
	public void setCreate_time(Date createTime) {
		this.createTime = createTime;
	}
	public String getSessionCode() {
		return sessionCode;
	}
	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public Integer getEquipment() {
		return equipment;
	}
	public void setEquipment(Integer equipment) {
		this.equipment = equipment;
	}
}
