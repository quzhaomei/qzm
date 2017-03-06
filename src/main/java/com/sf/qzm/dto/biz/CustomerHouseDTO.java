package com.sf.qzm.dto.biz;

import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.dto.admin.AdminUserDTO;

public class CustomerHouseDTO {
	/**
	 * 房子id
	 */
	private Integer houseId;
	/**
	 * 区域
	 */
	private Zone zone;
	/**
	 * 区域
	 */
	private CustomerDTO customer;
	/**
	 * 楼盘信息
	 */
	private String houseInfo;
	/**
	 * 房屋地址
	 */
	private String houseLocation;
	/**
	 * 房屋类型
	 */
	private HouseStyle houseStyle;
	/**
	 * 房屋类别 0-旧房，1-新房
	 */
	private Integer isNew;
	/**
	 * 房屋面积
	 */
	private Integer area;
	/**
	 * 装修类别 0-半包，1-全包
	 */
	private Integer designType;
	/**
	 * 装修预算
	 */
	private Budget budget;
	/**
	 * 是否有软装需求 0-没有，1-有
	 */
	private Integer hasSoft;
	/**
	 * 客户备注
	 */
	private String comment;
	/**
	 * 操作日志
	 */
	private String log;
	
	private Long createDate;
	private AdminUserDTO createUser;
	/**
	 * 状态，0-创建中,1-
	 */
	private Integer status;
	
	/**
	 * 0-未派单，1-已派单
	 */
	private Integer softStatus;
	/**
	 * 是否配送了礼包，0-否，1-是
	 */
	private Integer gift;
	/**
	 * 礼包地址
	 */
	private String giftAddress;
	
	private String callbackTips;
	
	
	
	//查询辅助字段
	private Integer orderNum;
	private Integer orderUnAccept;
	
	public String getCallbackTips() {
		return callbackTips;
	}
	public void setCallbackTips(String callbackTips) {
		this.callbackTips = callbackTips;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public String getHouseInfo() {
		return houseInfo;
	}
	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}
	public String getHouseLocation() {
		return houseLocation;
	}
	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}
	public HouseStyle getHouseStyle() {
		return houseStyle;
	}
	public void setHouseStyle(HouseStyle houseStyle) {
		this.houseStyle = houseStyle;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	public Budget getBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public Integer getHasSoft() {
		return hasSoft;
	}
	public void setHasSoft(Integer hasSoft) {
		this.hasSoft = hasSoft;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getGift() {
		return gift;
	}
	public void setGift(Integer gift) {
		this.gift = gift;
	}
	public String getGiftAddress() {
		return giftAddress;
	}
	public void setGiftAddress(String giftAddress) {
		this.giftAddress = giftAddress;
	}
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getOrderUnAccept() {
		return orderUnAccept;
	}
	public void setOrderUnAccept(Integer orderUnAccept) {
		this.orderUnAccept = orderUnAccept;
	}
	public Integer getSoftStatus() {
		return softStatus;
	}
	public void setSoftStatus(Integer softStatus) {
		this.softStatus = softStatus;
	}
	
}
