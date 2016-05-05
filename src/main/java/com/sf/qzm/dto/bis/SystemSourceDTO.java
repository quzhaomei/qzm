package com.sf.qzm.dto.bis;

import java.util.Date;

import com.sf.qzm.dto.SfDto;

public class SystemSourceDTO implements SfDto{
	private Integer sourceId;//系统id
	private String key;// key
	private String value;//value
	private Integer type;//0-图片，1-文字
	private String description;//描述
	private Date createDate;
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
