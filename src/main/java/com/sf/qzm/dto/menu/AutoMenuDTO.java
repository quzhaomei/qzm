package com.sf.qzm.dto.menu;

import java.util.List;

public class AutoMenuDTO {
	private Integer menuId;
	private String title;
	private String url;
	private String icon;
	private String code;
	private Integer sequence;
	private Integer extend;//1-拓展的
	private List<AutoMenuDTO> childMenu;
	
	private Integer status;//0-有权限。1-无权限
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public List<AutoMenuDTO> getChildMenu() {
		return childMenu;
	}
	public void setChildMenu(List<AutoMenuDTO> childMenu) {
		this.childMenu = childMenu;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getExtend() {
		return extend;
	}
	public void setExtend(Integer extend) {
		this.extend = extend;
	}
	
}
