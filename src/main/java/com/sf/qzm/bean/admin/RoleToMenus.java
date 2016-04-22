package com.sf.qzm.bean.admin;

import com.sf.qzm.bean.SfBean;

/**
 * 角色-路径
 * 
 * @author qzm
 *
 * @since 2015-6-10
 */
public class RoleToMenus implements SfBean{
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer roleId;

	/**
	 * 菜单id
	 */
	private Integer menuId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

}
