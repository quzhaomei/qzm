package com.sf.qzm.dto.biz;

import java.util.List;

import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.dto.admin.AdminUserDTO;

/**
 * 客户表
 * @author quzhaomei
 *
 */
public class CustomerDTO {
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
	 * 状态 1-未分单，2-已分单
	 */
	private Integer status;
	/**
	 * 渠道
	 */
	private ChannelDTO channel;
	/**
	 * 业务员
	 */
	private AdminUserDTO servicer;
	private Long serviceDate;
	
	private Age age;
	private String carCode;
	private Integer integration;
	
	private AdminUserDTO createUser;
	/**
	 * 创建时间
	 */
	private Long createDate;
	
	private String info;
	//下次回访时间
	private Long nextcallTime;
	private Long fileDate;
	private String nextcallInfo;
	
	private String gender;
	
	private List<CustomerHouseDTO> houses;
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
	public ChannelDTO getChannel() {
		return channel;
	}
	public void setChannel(ChannelDTO channel) {
		this.channel = channel;
	}
	
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
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
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Age getAge() {
		return age;
	}
	public void setAge(Age age) {
		this.age = age;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public Integer getIntegration() {
		return integration;
	}
	public void setIntegration(Integer integration) {
		this.integration = integration;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public List<CustomerHouseDTO> getHouses() {
		return houses;
	}
	public void setHouses(List<CustomerHouseDTO> houses) {
		this.houses = houses;
	}
	public AdminUserDTO getServicer() {
		return servicer;
	}
	public void setServicer(AdminUserDTO servicer) {
		this.servicer = servicer;
	}
	public Long getNextcallTime() {
		return nextcallTime;
	}
	public void setNextcallTime(Long nextcallTime) {
		this.nextcallTime = nextcallTime;
	}
	public Long getFileDate() {
		return fileDate;
	}
	public void setFileDate(Long fileDate) {
		this.fileDate = fileDate;
	}
	public String getNextcallInfo() {
		return nextcallInfo;
	}
	public void setNextcallInfo(String nextcallInfo) {
		this.nextcallInfo = nextcallInfo;
	}
	public Long getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(Long serviceDate) {
		this.serviceDate = serviceDate;
	}
	
}
