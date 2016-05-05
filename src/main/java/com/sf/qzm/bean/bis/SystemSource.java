package com.sf.qzm.bean.bis;

import java.util.Date;

/**
 * 系统资源管理
 * key-source
 * @author quzhaomei
 *
 */
public class SystemSource {
	private Integer sourceId;//系统id
	private String key;// key
	private String value;//value
	private Integer type;//0-图片，1-文字
	private String description;//描述
	private Date createDate;
	
	public static final Integer TYPE_IMG=0;
	public static final Integer TYPE_STRING=1;
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
