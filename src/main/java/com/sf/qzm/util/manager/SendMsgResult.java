package com.sf.qzm.util.manager;

public class SendMsgResult {
	private Integer success;//0：失败，1:成功
	private String message;
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
