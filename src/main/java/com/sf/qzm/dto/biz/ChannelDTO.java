package com.sf.qzm.dto.biz;

import com.sf.qzm.dto.admin.AdminUserDTO;

public class ChannelDTO {
	private Integer channelId;
	private String name;//渠道名字
	private String code;//渠道编码,唯一
	private Integer status;//0-冻结，1-激活
	private Long createDate;
	private AdminUserDTO createUser;
	private String info;//备注
	private ChannelTypeDTO channelType;
	
	public ChannelTypeDTO getChannelType() {
		return channelType;
	}
	public void setChannelType(ChannelTypeDTO channelType) {
		this.channelType = channelType;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
