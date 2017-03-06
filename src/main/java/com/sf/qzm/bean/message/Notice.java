package com.sf.qzm.bean.message;

import java.util.Date;

/**
 * 通知
 * @author quzhaomei
 *
 */
public class Notice {
	private String url;
	private String title;
	private String message;
	private Date createDate;
	public String getUrl() {
		return url;
	}
	public Notice setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public Notice setMessage(String message) {
		this.message = message;
		return this;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Notice setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
