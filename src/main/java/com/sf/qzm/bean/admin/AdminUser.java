package com.sf.qzm.bean.admin;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.sf.qzm.bean.SfBean;

/**
 * 系统人员类，一个系统人员，可以对应多个角色
 * 
 * @author qzm
 * @since 2015-6-10
 */
public class AdminUser implements SfBean {
	/**
	 * 主键
	 */
	private Integer adminUserId;
	private String avatar;
	/**
	 * 登录名－30
	 */
	private String loginname;
	/**
	 * 登录密码
	 */
	private String password;
	/**
	 * 用户昵称 30
	 */
	private String nickname;

	/**
	 * 用户电话 unique 11
	 */
	private String phone;

	/**
	 * 用户邮箱 unique 11
	 */
	private String email;

	/**
	 * 状态 0-冻结，1-有效，
	 */
	private Integer status;

	/**
	 * 备注 200
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	/**
	 * 创建者
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



	
	/*辅助字段 －－－ start*/
	
	private List<Integer> roleIds;//角色id集合
	private Integer roleId;//角色id
	/*辅助字段 --- end*/
	
	/* 常数区域 －－－start */
	public static final int STATUS_DELETE = 0;
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_FROZEN = 2;

	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_ADMIN = 1;

	public static final int ORINAL_PERSONAL = 0;
	public static final int ORINAL_ENTERPRISE = 1;
	/* 常数区域 －－－end */

	
	private Integer isDelete;//1是，0否

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

	public Integer getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getEmail() {
		return email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public Integer getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
