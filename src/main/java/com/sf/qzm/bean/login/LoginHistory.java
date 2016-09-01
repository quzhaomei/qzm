package com.sf.qzm.bean.login;

import java.util.Date;

public class LoginHistory {
	public static Integer STATUS_LOGIN_OUT=0;
	public static Integer STATUS_LOGIN_IN=1;
	private Integer historyId;
	private Integer adminUserId;
	private String sessionId;
	private String ip;
	private Date logininTime;
	private Date loginoutTime;
	private Integer status;//1-登录中，0-已退出
	private String message;//备注！
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Integer getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(Integer adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getLogininTime() {
		return logininTime;
	}
	public void setLogininTime(Date logininTime) {
		this.logininTime = logininTime;
	}
	public Date getLoginoutTime() {
		return loginoutTime;
	}
	public void setLoginoutTime(Date loginoutTime) {
		this.loginoutTime = loginoutTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
 }
