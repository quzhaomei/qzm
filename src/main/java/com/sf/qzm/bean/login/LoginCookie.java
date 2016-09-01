package com.sf.qzm.bean.login;

import java.util.Date;

public class LoginCookie {
	private Integer cookieId;
	private String loginname;
	private String ip;
	private Date createDate;
	public Integer getCookieId() {
		return cookieId;
	}
	public void setCookieId(Integer cookieId) {
		this.cookieId = cookieId;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
