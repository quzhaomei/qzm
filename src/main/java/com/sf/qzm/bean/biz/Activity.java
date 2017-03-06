package com.sf.qzm.bean.biz;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 活动
 * @author quzhaomei
 */
public class Activity {
	private Integer activityId;
	private String name;
	private List<ActivityImage> images;//活动图片1
	private Integer createUserId;
	private Integer status;//
	private Date createDate;
	private String info;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	/**
	 * 表单选项
	 */
	private Integer hasName;
	private Integer hasPhone;
	
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
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<ActivityImage> getImages() {
		return images;
	}
	public void setImages(List<ActivityImage> images) {
		this.images = images;
	}
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
	
}
