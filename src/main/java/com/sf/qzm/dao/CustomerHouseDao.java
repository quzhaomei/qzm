package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.biz.CustomerHouseDTO;

public interface CustomerHouseDao {
	void save(@Param(value="house") CustomerHouse house);
	void update(@Param(value="house")CustomerHouse house);
	
	List<CustomerHouse> list(@Param(value="house")CustomerHouse house);
	
	CustomerHouse get(@Param(value="house") CustomerHouse house);
	
	List<CustomerHouseDTO> listByService(@Param(value="serviceId")Integer serviceId);
	
}
