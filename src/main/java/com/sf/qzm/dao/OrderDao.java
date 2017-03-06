package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CompanyServiceDTO;
import com.sf.qzm.dto.biz.OrderDTO;

public interface OrderDao {
	void save(@Param(value="order") Order order);
	void update(@Param(value="order")Order order);
	OrderDTO get(@Param(value="order")Order order);
	List<OrderDTO> list(@Param(value="page")PageDTO<Order> page,@Param(value="keeperId")Integer keeperId,
			@Param(value="companyTypeName")String companyTypeName,@Param(value="houseStyleName")String houseStyleName);
	int count(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId,
			@Param(value="companyTypeName")String companyTypeName,@Param(value="houseStyleName")String houseStyleName);
	
	
	int totalPrice(@Param(value="order")Order order,@Param(value="keeperId")Integer keeperId,
			@Param(value="companyTypeName")String companyTypeName,@Param(value="houseStyleName")String houseStyleName);
	
	
	List<CompanyServiceDTO> getCompanyService(@Param(value="page")PageDTO<CustomerHouse> page,
			@Param(value="zoneId")Integer zoneId,@Param(value="typeId")Integer typeId,@Param(value="storeName")String storeName);
	
	int getCompanyServiceCount(@Param(value="house")CustomerHouse house,
			@Param(value="zoneId")Integer zoneId,@Param(value="typeId")Integer typeId,@Param(value="storeName")String storeName);
	
}
