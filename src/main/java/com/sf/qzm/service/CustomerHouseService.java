package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;

public interface CustomerHouseService {
	CustomerHouse get(Integer houseId);
	CustomerHouseDTO getDTO(Integer houseId);
	void saveOrUpdate(CustomerHouse house);
	List<CustomerHouse> list(Integer customerId);
	int count(Integer serviceId,CustomerHouse house,Integer typeId);
	
	
	PageDTO<List<CustomerHouseDTO>> listByServiceAndPage(Integer serviceId,PageDTO<CustomerHouse> page,
			String customerName,String customerCode,Integer typeId);
	
	PageDTO<List<CustomerHouseDTO>> softListByServiceAndPage(Integer serviceId,PageDTO<CustomerHouse> page,
			String customerName,String customerCode,Integer typeId);
	
	PageDTO<List<CustomerHouseDTO>> listAllByServiceAndPage(Integer serviceId,PageDTO<CustomerHouse> page,
			String customerName,String customerCode,Integer typeId);
	
	
	int allCount(CustomerHouse house,Integer serviceId);
}
