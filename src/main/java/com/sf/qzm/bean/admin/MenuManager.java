package com.sf.qzm.bean.admin;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sf.qzm.bean.SfBean;

/**
 * @author qzm
 * @since 2015-6-10
 */
public class MenuManager implements SfBean{
	/**
	 * 主键
	 */
	private Integer menuId;

	/**
	 * 菜单名字
	 */
	private String menuName;

	/**
	 * 菜单链接，
	 */
	private String uri;

	/**
	 * 父节点id -1 表示没有，
	 */
	private Integer parentId;

	/**
	 * 菜单类型 0-菜单组，1-页面链接，2-操作
	 */
	private Integer type;

	/**
	 * 0-删除 1-启用
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
	public static final Integer NO_PARENT = -1;
	public static final Integer TYPE_TEAM = 0;
	public static final Integer TYPE_PAGE = 1;
	public static final Integer TYPE_OPERATOR = 2;

	public static final Integer STATUS_DELETE = 0;
	public static final Integer STATUS_ACTIVE = 1;

	/* 常数区域 －－end */

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
