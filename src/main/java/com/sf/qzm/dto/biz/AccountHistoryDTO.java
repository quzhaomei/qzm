package com.sf.qzm.dto.biz;

import com.sf.qzm.dto.admin.AdminUserDTO;

public class AccountHistoryDTO {
	private Integer historyId;
	private CompanyDTO company;
	private DesignerDTO designer;
	private Integer type;//1-公司，2-设计师
	private Integer tag;//1-消费，2-充值
	private Integer orderId;//订单号
	private Integer price;
	
	private Integer account;//操作后余额
	private AdminUserDTO createUser;
	private Long createDate;
	private String comment;//备注
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	public DesignerDTO getDesigner() {
		return designer;
	}
	public void setDesigner(DesignerDTO designer) {
		this.designer = designer;
	}
	public Integer getTag() {
		return tag;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public AdminUserDTO getCreateUser() {
		return createUser;
	}
	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
