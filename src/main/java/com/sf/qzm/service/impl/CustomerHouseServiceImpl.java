package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dao.CustomerHouseDao;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.service.CustomerHouseService;

@Service
@Transactional
public class CustomerHouseServiceImpl implements CustomerHouseService {
	@Resource
	private CustomerHouseDao customerHouseDao;

	@Override
	public void saveOrUpdate(CustomerHouse house) {
		if(house.getHouseId()!=null){
			customerHouseDao.update(house);
		}else{
			customerHouseDao.save(house);
		}
	}

	@Override
	public List<CustomerHouse> list(Integer customerId) {
		if(customerId==null){return null;}
		CustomerHouse cu=new CustomerHouse();
		cu.setCustomerId(customerId);
		return customerHouseDao.list(cu);
	}

	@Override
	public CustomerHouse get(Integer houseId) {
		if(houseId==null){return null;}
		CustomerHouse house=new CustomerHouse();
		house.setHouseId(houseId);
		return customerHouseDao.get(house);
	}

	@Override
	public List<CustomerHouseDTO> listByService(Integer serviceId) {
		return customerHouseDao.listByService(serviceId);
	}
	
}
