package com.sf.qzm.dto.menu;

import java.util.List;

public class PowerMenuDTO {
	private Integer menuId;
	private String name;
	private Integer status;
	private List<PowerMenuDTO> childMenu;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<PowerMenuDTO> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<PowerMenuDTO> childMenu) {
		this.childMenu = childMenu;
	}
	public PowerMenuDTO() {
	}
	public PowerMenuDTO(Integer menuId, Integer status) {
		super();
		this.menuId = menuId;
		this.status = status;
	}
	
}
