package com.sf.qzm.bean.biz;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户表
 * @author quzhaomei
 *
 */
public class Customer {
	/**
	 * 客户id
	 */
	private Integer customerId;
	
	private Integer customerCode;
	/**
	 * 客户号码
	 */
	private String phone;
	/**
	 * 客户名字
	 */
	private String name;
	/**
	 * 状态，1-待分配，2-等待回访，3-待跟进，4-关闭,5－已回访
	 */
	private Integer status;
	/**
	 * 性别 0-女，1-男
	 */
	private Integer gender;
	/**
	 * 渠道
	 */
	private Integer channelId;
	
	/**
	 * 业务员Id
	 */
	private Integer serviceId;
	
	private Date serviceDate;
	/**
	 * 车牌
	 */
	private String carCode;
	/**
	 * 年龄
	 */
	private Integer ageId;
	/**
	 * 积分
	 */
	private Integer integration;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	
	private Integer createUserId;
	
	private String info;
	
	
	//下次回访时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date nextcallTime;
	private Date fileDate;//处理时间
	private String nextcallInfo;//回访备注
	
	
	
	
	private List<Integer> houseId;
	
	/**
	 * 辅助字段
	 */
	private List<CustomerHouse> houses;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_start;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_end;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date serviceDate_start;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date serviceDate_end;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date nextcallInfo_start;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date nextcallInfo_end;
	private String key;
	private String channel;//渠道名称
	private String createUser;//创建者
	private String houseLocation;
	
	
	
	
	public String getHouseLocation() {
		return houseLocation;
	}
	public void setHouseLocation(String houseLocation) {
		this.houseLocation = houseLocation;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Integer getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(Integer customerCode) {
		this.customerCode = customerCode;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public Integer getAgeId() {
		return ageId;
	}
	public void setAgeId(Integer ageId) {
		this.ageId = ageId;
	}
	public Integer getIntegration() {
		return integration;
	}
	public void setIntegration(Integer integration) {
		this.integration = integration;
	}
	public List<Integer> getHouseId() {
		return houseId;
	}
	public void setHouseId(List<Integer> houseId) {
		this.houseId = houseId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public List<CustomerHouse> getHouses() {
		return houses;
	}
	public void setHouses(List<CustomerHouse> houses) {
		this.houses = houses;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Date getNextcallTime() {
		return nextcallTime;
	}
	public void setNextcallTime(Date nextcallTime) {
		this.nextcallTime = nextcallTime;
	}
	public Date getFileDate() {
		return fileDate;
	}
	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}
	public String getNextcallInfo() {
		return nextcallInfo;
	}
	public void setNextcallInfo(String nextcallInfo) {
		this.nextcallInfo = nextcallInfo;
	}
	public Date getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}
	public Date getCreateDate_start() {
		return createDate_start;
	}
	public void setCreateDate_start(Date createDate_start) {
		this.createDate_start = createDate_start;
	}
	public Date getCreateDate_end() {
		return createDate_end;
	}
	public void setCreateDate_end(Date createDate_end) {
		this.createDate_end = createDate_end;
	}
	public Date getServiceDate_start() {
		return serviceDate_start;
	}
	public void setServiceDate_start(Date serviceDate_start) {
		this.serviceDate_start = serviceDate_start;
	}
	public Date getServiceDate_end() {
		return serviceDate_end;
	}
	public void setServiceDate_end(Date serviceDate_end) {
		this.serviceDate_end = serviceDate_end;
	}
	public Date getNextcallInfo_start() {
		return nextcallInfo_start;
	}
	public void setNextcallInfo_start(Date nextcallInfo_start) {
		this.nextcallInfo_start = nextcallInfo_start;
	}
	public Date getNextcallInfo_end() {
		return nextcallInfo_end;
	}
	public void setNextcallInfo_end(Date nextcallInfo_end) {
		this.nextcallInfo_end = nextcallInfo_end;
	}
	
}
