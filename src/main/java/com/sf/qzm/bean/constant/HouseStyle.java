package com.sf.qzm.bean.constant;

import java.util.List;

/**
 * 房型管理
 * @author quzhaomei
 *
 */
public class HouseStyle {
	private Integer styleId;
	private String name;
	private Integer isDelete;
	
	private List<CompanyToStyle> defaultSet;
	
	private List<CompanyToStyle> companySet;
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public List<CompanyToStyle> getDefaultSet() {
		return defaultSet;
	}
	public void setDefaultSet(List<CompanyToStyle> defaultSet) {
		this.defaultSet = defaultSet;
	}
	public List<CompanyToStyle> getCompanySet() {
		return companySet;
	}
	public void setCompanySet(List<CompanyToStyle> companySet) {
		this.companySet = companySet;
	}
	
	
	
}
