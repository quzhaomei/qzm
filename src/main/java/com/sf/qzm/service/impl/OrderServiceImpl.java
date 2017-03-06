package com.sf.qzm.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.dao.CompanyTypeDao;
import com.sf.qzm.dao.OrderDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CompanyServiceDTO;
import com.sf.qzm.dto.biz.OrderDTO;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.util.other.StringUtils;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderDao orderDao;
	
	@Resource
	private CompanyTypeDao CompanyTypeDao;
	
	private SimpleDateFormat format=new SimpleDateFormat("yyMMddHHmm");
	//生成订单编号
	private String madeOrderCode(Order order){
		String orderCode="";
		try {
			CompanyType cType =CompanyTypeDao.getById(order.getTypeId());
			String prefix=StringUtils.getFirstChar(cType.getName());//前缀
			String type="0"+order.getType();//商户类型 01:公司，02设计师
			String unixTime=format.format(new Date());//时间戳
			String toId=order.getToId().toString();//
			String houseId=order.getHouseId().toString();//房子id
			orderCode=prefix+type+unixTime+toId+houseId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return orderCode;
	}
	@Override
	public void saveOrUpdate(Order order) {
		if(order.getOrderId()==null){
			order.setOrderCode(madeOrderCode(order));
			orderDao.save(order);
		}else{
			orderDao.update(order);
		}
	}

	@Override
	public PageDTO<List<CompanyServiceDTO>> getCompanyService(PageDTO<CustomerHouse> page, Integer zoneId,
			Integer typeId,String storeName) {
		List<CompanyServiceDTO> dateList=orderDao.getCompanyService(page,zoneId,typeId,storeName);
		
		PageDTO<List<CompanyServiceDTO>> pageDate = new PageDTO<List<CompanyServiceDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		int count=orderDao.getCompanyServiceCount(page.getParam(),zoneId,typeId,storeName);
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public PageDTO<List<OrderDTO>> list(PageDTO<Order> page, Integer keeperId,String companyTypeName,String houseStyleName) {
	List<OrderDTO> dateList=orderDao.list(page,keeperId, companyTypeName, houseStyleName);
		
		PageDTO<List<OrderDTO>> pageDate = new PageDTO<List<OrderDTO>>();
		pageDate.setParam(dateList);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		int count=orderDao.count(page.getParam(),keeperId, companyTypeName, houseStyleName);
		pageDate.setCount(count+0);
		count = count % page.getPageSize() == 0 ? count / page.getPageSize()
				: count / page.getPageSize() + 1;
		pageDate.setTotalPage(count);
		return pageDate;
	}

	@Override
	public OrderDTO getById(Integer id) {
		if(id==null){return null;}
		Order param=new Order();
		param.setOrderId(id);
		return orderDao.get(param);
	}

	@Override
	public Integer totalPrice(Order order, Integer keeperId, String companyTypeName, String houseStyleName) {
		return orderDao.totalPrice(order, keeperId, companyTypeName, houseStyleName);
	}
	@Override
	public int count(Order order) {
		return orderDao.count(order, null, null, null);
	}
	
}
