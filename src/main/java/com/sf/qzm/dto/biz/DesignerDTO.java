package com.sf.qzm.dto.biz;

import com.sf.qzm.dto.admin.AdminUserDTO;

public class DesignerDTO {
	/**
	 * 主键
	 */
	private Integer designerId;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 号码
	 */
	private String phone;
	/**
	 * 管理帐号
	 */
	private AdminUserDTO manager;
	/**
	 * 公司名字
	 */
	private String companyName;
	/**
	 * 接单状态
	 */
	private Integer accept;
	/**
	 * 创建时间
	 */
	private Long createDate;
	/**
	 * 创建用户
	 */
	private AdminUserDTO createUser;
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public AdminUserDTO getManager() {
		return manager;
	}
	public void setManager(AdminUserDTO manager) {
		this.manager = manager;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public Integer getAccept() {
		return accept;
	}
	public void setAccept(Integer accept) {
		this.accept = accept;
	}
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
}
