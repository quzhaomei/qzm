package com.sf.qzm.bean.bis;

import com.sf.qzm.bean.SfBean;

/**
 * 聊天记录
 * @author quzhaomei
 */
public class TalkHistory implements SfBean {
	public static final Integer STATUS_UN_ACCEPT=0;
	public static final Integer STATUS_ACCEPT=1;
	
	private Integer historyId;
	private String fromId;
	private String fromName;
	private String fromHeadImg;
	private String toId;
	private String toName;
	private String toHeadImg;
	private String message;
	private Long createDate;
	private Integer status;//0-未接受，1-已接受
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getFromHeadImg() {
		return fromHeadImg;
	}
	public void setFromHeadImg(String fromHeadImg) {
		this.fromHeadImg = fromHeadImg;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getToHeadImg() {
		return toHeadImg;
	}
	public void setToHeadImg(String toHeadImg) {
		this.toHeadImg = toHeadImg;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
