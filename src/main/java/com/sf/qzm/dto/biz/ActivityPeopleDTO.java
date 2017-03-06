package com.sf.qzm.dto.biz;

public class ActivityPeopleDTO {
	private Integer peopleId;
	private String name;
	private String phone;
	private Long createDate;
	private ChannelDTO channelDTO;
	private ActivityDTO activityDTO;
	public Integer getPeopleId() {
		return peopleId;
	}
	public void setPeopleId(Integer peopleId) {
		this.peopleId = peopleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public ChannelDTO getChannelDTO() {
		return channelDTO;
	}
	public void setChannelDTO(ChannelDTO channelDTO) {
		this.channelDTO = channelDTO;
	}
	public ActivityDTO getActivityDTO() {
		return activityDTO;
	}
	public void setActivityDTO(ActivityDTO activityDTO) {
		this.activityDTO = activityDTO;
	}
	
}
