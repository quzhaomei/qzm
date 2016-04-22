package com.sf.qzm.bean.admin;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sf.qzm.bean.SfBean;

/**
 * @author qzm
 * @since 2015-6-10
 */
public class RoleManager implements SfBean {
	/**
	 * 主键
	 */
	private Integer roleId;

	/**
	 * 角色名字
	 */
	private String roleName;

	/**
	 * 状态 0-无效，1-激活，
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	/**
	 * 创建者id
	 */
	private Integer createUserId;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;

	/**
	 * 更新者id
	 */
	private Integer updateUserId;

	/* 常数区域 －－start */
	public static final Integer STATUS_FROZEN = 0;
	public static final Integer STATUS_ACTIVE = 1;
	/* 常数区域 －－end */

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

}
