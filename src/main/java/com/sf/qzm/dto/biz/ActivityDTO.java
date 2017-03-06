package com.sf.qzm.dto.biz;

import java.util.List;

import com.sf.qzm.bean.biz.ActivityImage;
import com.sf.qzm.dto.admin.AdminUserDTO;

/**
 * 活动
 * @author quzhaomei 
 */
public class ActivityDTO {
	private Integer activityId;
	private String name;
	private String info;
	
	private AdminUserDTO createUser;
	private Integer status;//
	private Long startDate;
	private Long endDate;
	private Long createDate;
	private List<ActivityImage> images;
	
	/**
	 * 表单选项
	 */
	private Integer hasName;
	private Integer hasPhone;
	
	
	public Integer getHasName() {
		return hasName;
	}
	public void setHasName(Integer hasName) {
		this.hasName = hasName;
	}
	public Integer getHasPhone() {
		return hasPhone;
	}
	public void setHasPhone(Integer hasPhone) {
		this.hasPhone = hasPhone;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
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
	public List<ActivityImage> getImages() {
		return images;
	}
	public void setImages(List<ActivityImage> images) {
		this.images = images;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	
}
