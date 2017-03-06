package com.sf.qzm.controller.biz;

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
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.AccountHistoryDTO;
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
@RequestMapping("order")
@MenuTag(code = "order", name = "订单结算系统", sequence = 6, type = 0,icon="icon-coin-yen")
public class OrderController extends BaseController {
	@Resource
	private CompanyService companyService;
	
	@Resource
	private AccountHistoryService accountHistoryService;
	@MenuTag(code = "account-history", name = "账户结算管理", sequence = 1, type = 1 )
	@RequestMapping(value = "account-history")
	public String accountHistory(String operator,
			Integer companyId,
			Map<String,Object> map){
		if (Constant.LIST.equals(operator)) {
			List<CompanyDTO> list = companyService.all();
			map.put(Constant.JSON, JsonUtils.object2json(list));
			return Constant.JSON;
		}else if("history".equals(operator)){
			Integer pageIndex=1;
			Integer pageSize=20;//前20条
			PageDTO<AccountHistory> page=new PageDTO<AccountHistory>();
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			AccountHistory param=new AccountHistory();
			param.setToId(companyId);
			param.setType(1);//公司
			page.setParam(param);
			PageDTO<List<AccountHistoryDTO>> result=accountHistoryService.getHistory(page);
			map.put(Constant.JSON, JsonUtils.object2json(result));
			return Constant.JSON;
		}
		return "admin/account-history";
	}
	
	@MenuTag(code = "account-history-add", name = "店铺账户充值", sequence = 1, type = 2,parentCode="account-history" )
	@RequestMapping(value = "account-history-add")
	@ResponseBody
	public JsonDTO accountHistoryAdd(HttpServletRequest request, Integer companyId,Integer price,String comment){
		JsonDTO json=new JsonDTO();
		try {
			accountHistoryService.saveCompanyAccount(companyId, price, getLoginAdminUser(request).getAdminUserId(), comment);
			json.setStatus(1).setMessage("充值成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("充值失败！");
		}
		
		return json;
	}
	
	@Resource
	private OrderService orderService;
	@Resource
	private CustomerHouseService customerHouseService;
	@Resource
	private ZoneService zoneService;
	
	public static final String ORDER_ALL="order-all";
	@MenuTag(code = ORDER_ALL, name = "全部订单", sequence = 2, type = 1 )
	@RequestMapping(value = "order-all")
	public String orderAll(String operator,
			Order order,String key,
			Integer pageIndex,
			String sort,String direction,
			Integer pageSize,
			String companyTypeName,String houseStyleName,
			Integer orderId,
			Map<String, Object> map,HttpServletRequest request){
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
			PageDTO<List<OrderDTO>> datas=orderService.list(page,null,companyTypeName,houseStyleName);
			
			//统计
			Map<String,Object> statistics=new HashMap<String, Object>();
			statistics.put("totalPrice", orderService.totalPrice(order, null, companyTypeName, houseStyleName));
			datas.setStatistics(statistics);
			
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
		return "admin/all-order-list";
	}

	
}
