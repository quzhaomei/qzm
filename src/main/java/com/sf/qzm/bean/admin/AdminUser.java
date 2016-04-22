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
	 * 状态 0-删除，1-有效，2-冻结
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

	/**
	 * 账户类型 0-普通帐号，1-管理帐号，管理账号通用，普通账号只能登陆前台
	 */
	private Integer type;

	/**
	 * 来源，0-个人，1-企业
	 */
	private Integer orinal;

	/**
	 * 公司地址 50
	 */
	private String position;

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

	public Integer getOrinal() {
		return orinal;
	}

	public void setOrinal(Integer orinal) {
		this.orinal = orinal;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	
}
