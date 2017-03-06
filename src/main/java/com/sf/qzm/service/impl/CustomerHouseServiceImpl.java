package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dao.CustomerHouseDao;
import com.sf.qzm.dto.PageDTO;
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
	public PageDTO<List<CustomerHouseDTO>> listByServiceAndPage(Integer serviceId, PageDTO<CustomerHouse> page,
			String customerName, String customerCode,Integer typeId) {
		List<CustomerHouseDTO> dateList =customerHouseDao.listByServiceAndPage(serviceId, page, customerName, customerCode,typeId);
		PageDTO<List<CustomerHouseDTO>> pageDate = new PageDTO<List<CustomerHouseDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = customerHouseDao.countByService(serviceId, page.getParam(), customerName, customerCode,typeId);
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	
	
	@Override
	public PageDTO<List<CustomerHouseDTO>> softListByServiceAndPage(Integer serviceId, PageDTO<CustomerHouse> page,
			String customerName, String customerCode, Integer typeId) {
		List<CustomerHouseDTO> dateList =customerHouseDao.softListByServiceAndPage(serviceId, page, customerName, customerCode,typeId);
		PageDTO<List<CustomerHouseDTO>> pageDate = new PageDTO<List<CustomerHouseDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = customerHouseDao.softCountByService(serviceId, page.getParam(), customerName, customerCode,typeId);
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public CustomerHouseDTO getDTO(Integer houseId) {
		if(houseId==null){return null;}
		CustomerHouse house=new CustomerHouse();
		house.setHouseId(houseId);
		return customerHouseDao.getDTO(house);
	}

	@Override
	public int count(Integer serviceId, CustomerHouse house, Integer typeId) {
		return customerHouseDao.countByService(serviceId, house, null, null, typeId);
	}

	@Override
	public int allCount(CustomerHouse house, Integer serviceId) {
		return customerHouseDao.count(house, serviceId);
	}

	@Override
	public PageDTO<List<CustomerHouseDTO>> listAllByServiceAndPage(Integer serviceId, PageDTO<CustomerHouse> page,
			String customerName, String customerCode, Integer typeId) {
		List<CustomerHouseDTO> dateList =customerHouseDao.listAllByServiceAndPage(serviceId, page, customerName, customerCode,typeId);
		PageDTO<List<CustomerHouseDTO>> pageDate = new PageDTO<List<CustomerHouseDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = customerHouseDao.countAllByService(serviceId, page.getParam(), customerName, customerCode,typeId);
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}


	
}
