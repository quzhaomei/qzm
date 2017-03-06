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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.dto.biz.OrderDTO;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("myOrder")
@MenuTag(code = "myOrder", name = "订单跟踪", sequence = 5, type = 0,icon="icon-clipboard")
public class MyOrderController extends BaseController {
	
	@Resource
	private OrderService orderService;
	@Resource
	private CustomerHouseService customerHouseService;
	@Resource
	private ZoneService zoneService;
	public static final String MY_ORDER="service-order";
	@MenuTag(code = MY_ORDER, name = "订单追踪", sequence = 2, type = 1 )
	@RequestMapping(value = "service-order")
	public String serviceOrder(String operator,
			Order order,String key,
			Integer pageIndex,
			String sort,String direction,
			Integer pageSize,
			String companyTypeName,String houseStyleName,
			Integer orderId,
			Map<String, Object> map,HttpServletRequest request){
		if(Constant.LIST.equals(operator)){
			PageDTO<Order> page=new PageDTO<Order>();
			order.setCreateUserId(getLoginAdminUser(request).getAdminUserId());//我负责的所有订单
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setParam(order);
			if(key!=null){companyTypeName=key;}
			if(sort==null){sort="createDate";}
			page.setOrderBy(sort);
			if(direction==null){direction="asc";}
			page.setDirection(direction);
			PageDTO<List<OrderDTO>> datas=orderService.list(page,null,companyTypeName,houseStyleName);
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
		return "admin/service-order-list";
	}
	
	@MenuTag(code = "service-order-change-price", name = "订单改价", sequence = 2, type = 2,parentCode=MY_ORDER )
	@RequestMapping(value = "service-order-change-price")
	@ResponseBody
	public JsonDTO serviceOrderChangePrice(@RequestParam(value="orderId") Integer orderId
			,@RequestParam(value="price")Integer price,HttpServletRequest request){
		JsonDTO result=new JsonDTO();
		OrderDTO order=orderService.getById(orderId);
		if(order!=null){
		
			if(order.getAcceptStatus()!=1){
				String log=order.getLog() + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
						+ getLoginAdminUser(request).getNickname() + "更改订单重新定价为" +price+ "&#13;";
				//修改订单价格
				Order param=new Order();
				param.setPrice(price);
				param.setOrderId(orderId);
				param.setLog(log);
				try {
					orderService.saveOrUpdate(param);
					result.setStatus(1).setMessage("订单改价成功！");
				} catch (Exception e) {
					result.setStatus(0).setMessage("系统出现异常！");
					e.printStackTrace();
				}
			}else{
				result.setStatus(0).setMessage("订单已经被接受，无法更改！");
			}
		}else{
			result.setStatus(0).setMessage("订单不存在！");
		}
		
		return result;
	}
}
