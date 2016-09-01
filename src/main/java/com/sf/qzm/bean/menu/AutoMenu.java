package com.sf.qzm.bean.menu;

import java.util.Date;

public class AutoMenu {
	/**
	 * 菜单id
	 */
	private Integer menuId;
	/**
	 * 菜单名字
	 */
	private String name;
	/**
	 * 菜单code   －－不能改变
	 */
	private String code;
	/**
	 * 父菜单code
	 */
	private String parentCode;
	/**
	 * 菜单类型 0-菜单组，1-页面链接，2-操作
	 */
	private Integer type;
	/**
	 * 显示顺序
	 */
	private Integer sequence;
	private String servletUrl;
	private Date createDate;
	private String icon;
	
	private Integer extend;//1-拓展的
	/**
	 * 辅助字段
	 */
	private Integer tempId;
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getServletUrl() {
		return servletUrl;
	}
	public void setServletUrl(String servletUrl) {
		this.servletUrl = servletUrl;
	}
	public Integer getTempId() {
		return tempId;
	}
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getExtend() {
		return extend;
	}
	public void setExtend(Integer extend) {
		this.extend = extend;
	}
	
}
