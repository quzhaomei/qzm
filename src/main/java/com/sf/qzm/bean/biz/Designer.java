package com.sf.qzm.bean.biz;

import java.util.Date;

/**
 * 设计师管理
 * @author quzhaomei
 *
 */
public class Designer {
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
	private Integer managerId;
	/**
	 * 公司名字
	 */
	private String companyName;
	/**
	 * 接单状态
	 */
	private Integer accept;
	/**
	 * 接单区域
	 */
	private Integer[] zoneIds;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建用户
	 */
	private Integer createUserId;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer[] getZoneIds() {
		return zoneIds;
	}
	public void setZoneIds(Integer[] zoneIds) {
		this.zoneIds = zoneIds;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getAccept() {
		return accept;
	}
	public void setAccept(Integer accept) {
		this.accept = accept;
	}
	
}
