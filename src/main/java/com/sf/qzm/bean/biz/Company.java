package com.sf.qzm.bean.biz;

import java.util.Date;

/**
 * 商家管理
 * @author quzhaomei
 */
public class Company {
	private Integer companyId;
	/**
	 * 店铺类别,硬装修
	 */
	private Integer[] companyTypeIds;
	/**
	 * 公司名称
	 */
	private String  companyName;
	
	/**
	 * 店铺logo
	 */
	private String logo;
	/**
	 * 店铺名字
	 */
	private String storeName;
	/**
	 * 店铺电话
	 */
	private String storePhone;
	/**
	 * 店铺地址
	 */
	private String storeAddress;
	/**
	 * 店铺类别
	 */
	private Integer storeType;//0-普通店铺，1-旗舰店
	
	private Integer account;//账户余额
	
	private Integer keeperId;//负责人id
	
	private String keeperName;
	private String keeperPhone;
	private Integer accept;//0-不允许，1-允许
	private Integer status;//展示状态,0-关闭，1-开启，2-注销
	
	private Date createDate;
	private Integer createUserId;
	
	
	
	
	//辅助字段
	private Integer[] zoneIds;
 	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStorePhone() {
		return storePhone;
	}
	public void setStorePhone(String storePhone) {
		this.storePhone = storePhone;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public Integer getStoreType() {
		return storeType;
	}
	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}
	public String getKeeperName() {
		return keeperName;
	}
	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
	}
	public String getKeeperPhone() {
		return keeperPhone;
	}
	public void setKeeperPhonme(String keeperPhone) {
		this.keeperPhone = keeperPhone;
	}
	public Integer getAccept() {
		return accept;
	}
	public void setAccept(Integer accept) {
		this.accept = accept;
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
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public void setKeeperPhone(String keeperPhone) {
		this.keeperPhone = keeperPhone;
	}
	public Integer getKeeperId() {
		return keeperId;
	}
	public void setKeeperId(Integer keeperId) {
		this.keeperId = keeperId;
	}
	public Integer[] getZoneIds() {
		return zoneIds;
	}
	public void setZoneIds(Integer[] zoneIds) {
		this.zoneIds = zoneIds;
	}
	public Integer[] getCompanyTypeIds() {
		return companyTypeIds;
	}
	public void setCompanyTypeIds(Integer[] companyTypeIds) {
		this.companyTypeIds = companyTypeIds;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public Company(Integer companyId) {
		super();
		this.companyId = companyId;
	}
	public Company() {
		super();
	}
	
}
