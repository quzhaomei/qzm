package com.sf.qzm.controller.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.AccountHistory;
import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.dto.biz.OrderDTO;
import com.sf.qzm.service.AccountHistoryService;
import com.sf.qzm.service.CompanyService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("myCompanyOrder")
@MenuTag(code = "myCompanyOrder", name = "我的订单", sequence = 5, type = 0,icon="icon-insert_drive_file")
public class CompanyOrderController extends BaseController {
	
	@Resource
	private CompanyService companyService;
	@Resource
	private OrderService orderService;
	@Resource
	private CustomerHouseService customerHouseService;
	@Resource
	private ZoneService zoneService;
	/**
	 * 我的订单
	 */
	@MenuTag(code = "company-my-order", name = "我的订单", sequence = 5, type = 1)
	@RequestMapping(value = "/company-my-order")
	public String myOrder(String operator,
			Order order,String key,
			Integer pageIndex,
			String sort,String direction,
			Integer pageSize,
			String companyTypeName,String houseStyleName,
			Integer orderId,
			Map<String, Object> map,HttpServletRequest request) {
		if(Constant.LIST.equals(operator)){
			PageDTO<Order> page=new PageDTO<Order>();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setParam(order);
			if(key!=null){companyTypeName=key;}
			if(sort==null){sort="createDate";}
			page.setOrderBy(sort);
			if(direction==null){direction="asc";}
			page.setDirection(direction);
			PageDTO<List<OrderDTO>> datas=orderService.list(page,getLoginAdminUser(request).getAdminUserId()
					,companyTypeName,houseStyleName);
			map.put(Constant.JSON, JsonUtils.object2json(datas));
			return Constant.JSON;
		}else if("one".equals(operator)){
			OrderDTO data=orderService.getById(orderId);
			Map<String,Object> dataMap=new HashMap<String, Object>();
			if(data!=null){
				CustomerHouseDTO houseDTO=customerHouseService.getDTO(data.getHouse().getHouseId());
				//查找房子
				dataMap.put("house", houseDTO);
				
				//查找区域
				if(houseDTO.getZone()!=null){
					Zone zone=zoneService.get(houseDTO.getZone().getZoneId());
					if(zone!=null&&zone.getParentId()!=null){
						Zone parent=zoneService.get(zone.getParentId());
						dataMap.put("zone_parent", parent.getName());
						if(parent!=null&&parent.getParentId()!=null){
							Zone root=zoneService.get(parent.getParentId());
							dataMap.put("zone_root", root.getName());
						}
					}
				}
			}
			map.put(Constant.JSON, JsonUtils.object2json(dataMap));
			return Constant.JSON;
		}
		return "admin/my-order-list";
	}
	
	
	@Resource
	private AccountHistoryService accountHistoryService;
	
	@MenuTag(code = "company-my-order-get", name = "接单", sequence = 5, type = 2,parentCode="company-my-order")
	@RequestMapping(value = "/company-my-order-get")
	@ResponseBody
	public JsonDTO myOrderGet(Integer orderId,HttpServletRequest request){
		String log = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
				+ getLoginAdminUser(request).getNickname() + "进行接单" + "&#13;";
		JsonDTO result=new JsonDTO();
		OrderDTO order=orderService.getById(orderId);
		if(order!=null){
			CompanyDTO company=companyService.get(order.getCompany().getCompanyId());
			if(company!=null){
				Order param=new Order();
				param.setOrderId(orderId);
				param.setAcceptDate(new Date());
				param.setAcceptStatus(1);
				param.setLog(order.getLog()+log);
				
				Company comParam=new Company();
				comParam.setCompanyId(company.getCompanyId());
				comParam.setAccount(company.getAccount()-order.getPrice());//进行扣费
				
				AccountHistory account=new AccountHistory();
				account.setCreateDate(new Date());
				account.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				account.setOrderId(orderId);
				account.setToId(order.getCompany().getCompanyId());
				account.setType(1);//1-公司
				account.setTag(1);//1-消费
				account.setPrice(order.getPrice());//金额
				account.setAccount(company.getAccount()-order.getPrice());//余额
				try {
					companyService.saveOrUpdate(comParam);
					orderService.saveOrUpdate(param);
					accountHistoryService.save(account);//消费记录
					
					result.setStatus(1).setMessage("接单成功！");
				} catch (Exception e) {
					result.setStatus(0).setMessage("接单失败，系统后台出现异常");
					e.printStackTrace();
				}
				
			}else{
				result.setStatus(0).setMessage("店铺不存在！");
			}
		}else{
			result.setStatus(0).setMessage("订单不存在！");
		}
		return result;
	}
	@MenuTag(code = "company-my-order-get-result", name = "接单结果反馈", sequence =6, type = 2,parentCode="company-my-order")
	@RequestMapping(value = "/company-my-order-get-result")
	@ResponseBody
	public JsonDTO myOrderGetResult(Order order,HttpServletRequest request){
		JsonDTO result=new JsonDTO();
		try {
			order.setSuccessDate(new Date());
			orderService.saveOrUpdate(order);
			result.setStatus(1).setMessage("反馈成功！");
		} catch (Exception e) {
			result.setStatus(0).setMessage("系统出现异常！");
			e.printStackTrace();
		}
		return result;
	}
}
