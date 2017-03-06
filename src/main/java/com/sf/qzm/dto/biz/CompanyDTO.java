package com.sf.qzm.dto.biz;

import java.util.List;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.dto.admin.AdminUserDTO;

/**
 * 商家管理
 * @author quzhaomei
 */
public class CompanyDTO {
	private Integer companyId;
	/**
	 * 店铺类别
	 */
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
	
	private Integer account;
	/**
	 * 店铺地址
	 */
	private String storeAddress;
	/**
	 * 业务类别
	 */
	private List<CompanyType> companyTypes;
	/**
	 * 店铺类别
	 */
	private Integer storeType;//0-普通店铺，1-旗舰店
	
	private AdminUserDTO keeper;
	
	private String keeperName;
	private String keeperPhone;
	private Integer accept;//0-不允许，1-允许
	private Integer status;//展示状态
	
	private Long createDate;
	private AdminUserDTO createUser;
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
	public AdminUserDTO getKeeper() {
		return keeper;
	}
	public void setKeeper(AdminUserDTO keeper) {
		this.keeper = keeper;
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
	public void setKeeperPhone(String keeperPhone) {
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
	public List<CompanyType> getCompanyTypes() {
		return companyTypes;
	}
	public void setCompanyTypes(List<CompanyType> companyTypes) {
		this.companyTypes = companyTypes;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	
}
