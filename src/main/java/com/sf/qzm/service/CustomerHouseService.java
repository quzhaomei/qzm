package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.biz.CustomerHouseDTO;

public interface CustomerHouseService {
	CustomerHouse get(Integer houseId);
	void saveOrUpdate(CustomerHouse house);
	List<CustomerHouse> list(Integer customerId);
	
	
	List<CustomerHouseDTO> listByService(Integer serviceId);
}
