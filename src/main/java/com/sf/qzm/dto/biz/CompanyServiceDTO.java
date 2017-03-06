package com.sf.qzm.dto.biz;

import java.util.List;

import com.sf.qzm.bean.biz.CompanyType;

public class CompanyServiceDTO {
	private Integer companyId;
	private String storeName;
	private Integer storeFee;
	
	private Integer price;
	
	private Integer monthCount;
	private Integer orderId;
	private List<CompanyType> companyTypes;
	
	private Integer tempId;
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Integer getStoreFee() {
		return storeFee;
	}
	public void setStoreFee(Integer storeFee) {
		this.storeFee = storeFee;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}
	public Integer getTempId() {
		return tempId;
	}
	public void setTempId(Integer tempId) {
		this.tempId = tempId;
	}
	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}
	public void setCompanyTypes(List<CompanyType> companyTypes) {
		this.companyTypes = companyTypes;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
}
