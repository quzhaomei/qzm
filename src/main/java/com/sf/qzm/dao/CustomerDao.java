package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.Customer;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerDTO;

public interface CustomerDao {
	void save(@Param(value="customer") Customer customer);
	void update(@Param(value="customer")Customer customer);
	CustomerDTO get(@Param(value="customer")Customer customer);
	
	void fresh();
	
	
	List<CustomerDTO> getByIds(@Param(value="ids")Integer[] ids);
	
	List<CustomerDTO> list(@Param(value="customer")Customer customer);
	
	List<CustomerDTO> listByPage(@Param(value = "page") PageDTO<Customer> page);
	
	int count(@Param(value="customer")Customer customer);
	int nextCustomerCode();
	
	void updateHouse(@Param(value="house") CustomerHouse house);
}
