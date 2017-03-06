package com.sf.qzm.dto.biz;

import com.sf.qzm.dto.admin.AdminUserDTO;

public class CompanyToStylePriceDTO {
	private Integer companyId;
	private Integer storeName;
	private Integer price;
	private Integer styleId;
	private Long createDate;
	private AdminUserDTO createUser;
	
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public Integer getStoreName() {
		return storeName;
	}
	public void setStoreName(Integer storeName) {
		this.storeName = storeName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	
	
}
