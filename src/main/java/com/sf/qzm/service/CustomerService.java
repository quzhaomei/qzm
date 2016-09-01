package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.biz.Customer;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerDTO;

public interface CustomerService {
	void saveOrUpdate(Customer customer);
	CustomerDTO get(Integer id);
	CustomerDTO get(String phone);
	List<CustomerDTO> all();
	
	List<CustomerDTO> getByIds(Integer[] ids);
	
	List<CustomerDTO> getByService(Integer serviceId);
	void delete(Integer customerId);
	
	int nextCustomerCode();
	
	void updateHouse(CustomerHouse house);
	
	PageDTO<List<CustomerDTO>> listByPage(PageDTO<Customer> page);
}
