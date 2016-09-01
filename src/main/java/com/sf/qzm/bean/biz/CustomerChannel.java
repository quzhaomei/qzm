package com.sf.qzm.bean.biz;

/**
 * 用户渠道记录
 * @author quzhaomei
 *
 */
public class CustomerChannel {
	private Integer id;
	private Integer customerId;
	private Integer channelId;
	private Integer status;//0-无效，1-有效
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
