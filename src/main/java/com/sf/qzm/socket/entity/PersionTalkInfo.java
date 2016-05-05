package com.sf.qzm.socket.entity;
/**
 * 用户聊天信息
 * @author quzhaomei
 */
public class PersionTalkInfo {
	/**
	 * 信息id
	 */
	private Integer messageId;//信息id
	private String fromId;//来自于某一人
	private String toId;//发给某一人的
	private String message;//信息
	private Long datatime;//时间
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getDatatime() {
		return datatime;
	}
	public void setDatatime(Long datatime) {
		this.datatime = datatime;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	
}
