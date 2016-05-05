package com.sf.qzm.dto.bis;

import java.util.Date;

/**
 * 合作方注册
 * @author quzhaomei
 */
public class CooperationDTO {
	private Integer cooperationId;//合作方id
	private String code;//合作方key   唯一
	private String name;//合作方名字
	private String description;//描述
	private Integer status;//状态
	private Date createDate;//
	
	public Integer getCooperationId() {
		return cooperationId;
	}
	public void setCooperationId(Integer cooperationId) {
		this.cooperationId = cooperationId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
