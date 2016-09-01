package com.sf.qzm.bean.login;

public class LoginOutMessage {
	/**
	 * 0-强制登出,1-正常
	 * 
	 */
	private Integer type;
	private String message;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
