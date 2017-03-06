package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.Customer;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.dao.CustomerDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.service.CustomerService;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	@Override
	public void saveOrUpdate(Customer customer) {
		if(customer.getCustomerId()!=null){
			customerDao.update(customer);
		}else{
			customerDao.save(customer);
		}
	}

	@Override
	public CustomerDTO get(Integer id) {
		if(id==null){return null;}
		Customer customer=new  Customer();
		customer.setCustomerId(id);
		return customerDao.get(customer);
	}

	@Override
	public List<CustomerDTO> all() {
		return customerDao.list(new Customer());
	}

	@Override
	public void delete(Integer customerId) {
		
	}

	@Override
	public int nextCustomerCode() {
		return customerDao.nextCustomerCode();
	}

	@Override
	public CustomerDTO get(String phone) {
		if(phone==null){return null;}
		Customer customer=new  Customer();
		customer.setPhone(phone);
		
		return customerDao.get(customer);
	}

	@Override
	public List<CustomerDTO> getByService(Integer serviceId) {
		if (serviceId==null) {
			return null;
		}
		Customer param=new Customer();
		param.setServiceId(serviceId);
		return customerDao.list(param);
	}

	@Override
	public void updateHouse(CustomerHouse house) {
		if(house.getHouseId()==null){return;}
		customerDao.updateHouse(house);
	}

	@Override
	public PageDTO<List<CustomerDTO>> listByPage(PageDTO<Customer> page) {
		List<CustomerDTO> dateList = customerDao.listByPage(page);
		PageDTO<List<CustomerDTO>> pageDate = new PageDTO<List<CustomerDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		Integer count = customerDao.count(page.getParam());
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public List<CustomerDTO> getByIds(Integer[] ids) {
		return customerDao.getByIds(ids);
	}

	@Override
	public List<CustomerDTO> download(PageDTO<Customer> page) {
		return customerDao.listByPage(page);
	}

	@Override
	public void fresh() {
		customerDao.fresh();
	}

	@Override
	public int count(Customer customer) {
		return customerDao.count(customer);
	}

}
