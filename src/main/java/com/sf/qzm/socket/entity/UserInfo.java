package com.sf.qzm.socket.entity;

/**
 * 聊天用户信息
 * 
 * @author quzhaomei
 */
public class UserInfo {
	/**
	 * 用户标识，唯一
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 头像
	 */
	private String headImg;
	
	private String code;//渠道编码
	private String sign;//加密标志
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
