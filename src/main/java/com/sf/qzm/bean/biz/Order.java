package com.sf.qzm.bean.biz;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Order {
	private Integer orderId;
	
	private String orderCode;
	
	private Integer houseId;//房子id
	private Integer toId;//公司id或设计师id
	private Integer type;//1-公司接受，2-设计师接受
	
	private Integer typeId;//业务类别
	
	private Integer price;//价格
	
	private Integer createUserId;
	private Date createDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_start;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createDate_end;
	
	private Integer acceptStatus;//0-未接受，1-已接受
	private Date acceptDate;
	
	private Integer isSuccess;//0-未确认，1-成功，2-失败
	private Date successDate;
	private String info;
	private String log;//操作日志追踪
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public Integer getToId() {
		return toId;
	}
	public void setToId(Integer toId) {
		this.toId = toId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getAcceptStatus() {
		return acceptStatus;
	}
	public void setAcceptStatus(Integer acceptStatus) {
		this.acceptStatus = acceptStatus;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}
	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Date getSuccessDate() {
		return successDate;
	}
	public void setSuccessDate(Date successDate) {
		this.successDate = successDate;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Date getCreateDate_start() {
		return createDate_start;
	}
	public void setCreateDate_start(Date createDate_start) {
		this.createDate_start = createDate_start;
	}
	public Date getCreateDate_end() {
		return createDate_end;
	}
	public void setCreateDate_end(Date createDate_end) {
		this.createDate_end = createDate_end;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
}
