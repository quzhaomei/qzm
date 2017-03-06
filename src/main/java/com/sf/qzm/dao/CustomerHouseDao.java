package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;

public interface CustomerHouseDao {
	void save(@Param(value="house") CustomerHouse house);
	void update(@Param(value="house")CustomerHouse house);
	
	List<CustomerHouse> list(@Param(value="house")CustomerHouse house);
	
	CustomerHouse get(@Param(value="house") CustomerHouse house);
	
	CustomerHouseDTO getDTO(@Param(value="house") CustomerHouse house);
	
	int count(@Param(value="house")CustomerHouse house,@Param(value="serviceId")Integer serviceId);
	
	List<CustomerHouseDTO> listByServiceAndPage(@Param(value="serviceId")Integer serviceId, @Param(value="page")PageDTO<CustomerHouse> page,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	
	int countByService(@Param(value="serviceId")Integer serviceId, @Param(value="house")CustomerHouse house,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	

	List<CustomerHouseDTO> listAllByServiceAndPage(@Param(value="serviceId")Integer serviceId, @Param(value="page")PageDTO<CustomerHouse> page,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	int countAllByService(@Param(value="serviceId")Integer serviceId, @Param(value="house")CustomerHouse house,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	
	
	List<CustomerHouseDTO> softListByServiceAndPage(@Param(value="serviceId")Integer serviceId, @Param(value="page")PageDTO<CustomerHouse> page,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	
	int softCountByService(@Param(value="serviceId")Integer serviceId, @Param(value="house")CustomerHouse house,
			@Param(value="customerName")String customerName, @Param(value="customerCode")String customerCode
			,@Param(value="typeId")Integer typeId);
	
}
