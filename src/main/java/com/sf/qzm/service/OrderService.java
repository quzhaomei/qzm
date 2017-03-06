package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CompanyServiceDTO;
import com.sf.qzm.dto.biz.OrderDTO;

public interface OrderService {
	void saveOrUpdate(Order order);
	OrderDTO getById(Integer id);
	
	PageDTO<List<OrderDTO>> list(PageDTO<Order> page,Integer keeperId,
			String companyTypeName,String houseStyleName);
	
	Integer totalPrice(Order order,Integer keeperId,
			String companyTypeName,String houseStyleName);
	
	
	PageDTO<List<CompanyServiceDTO>> getCompanyService(PageDTO<CustomerHouse> page,
			Integer zoneId,Integer typeId,String storeName);
	
	int count(Order order);
}
