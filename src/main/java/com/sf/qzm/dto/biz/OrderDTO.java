package com.sf.qzm.dto.biz;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.dto.admin.AdminUserDTO;

public class OrderDTO {
	private Integer orderId;
	private String orderCode;
	private CustomerHouseDTO house;//房子id
	private CompanyDTO company;//公司id或设计师id
	private DesignerDTO designer;
	private AdminUserDTO createUser;
	
	private Integer type;//1-公司接受，2-设计师接受
	
	private CompanyType companyType;//业务类别
	
	private Integer price;//价格
	
	private Long createDate;
	
	private Integer acceptStatus;//0-未接受，1-已接受
	private Long acceptDate;
	
	private Integer isSuccess;//0-未确认，1-成功，2-失败
	private Long successDate;
	private String info;//订单备注
	
	private String log;//操作日志追踪

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public CustomerHouseDTO getHouse() {
		return house;
	}

	public void setHouse(CustomerHouseDTO house) {
		this.house = house;
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
	

	public AdminUserDTO getCreateUser() {
		return createUser;
	}

	public void setCreateUser(AdminUserDTO createUser) {
		this.createUser = createUser;
	}



	public Integer getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(Integer acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Long acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Long getSuccessDate() {
		return successDate;
	}

	public void setSuccessDate(Long successDate) {
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

	public CompanyType getCompanyType() {
		return companyType;
	}

	public void setCompanyType(CompanyType companyType) {
		this.companyType = companyType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
	
	
}
