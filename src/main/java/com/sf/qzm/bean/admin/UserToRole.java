package com.sf.qzm.bean.admin;

import com.sf.qzm.bean.SfBean;

/**
 * 用户角色，中间表
 * @author Administrator
 */
public class UserToRole implements SfBean{
	private Integer id;
	private Integer userId;
	private Integer roleId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
}
