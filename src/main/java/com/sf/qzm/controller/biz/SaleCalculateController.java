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
import com.sf.qzm.bean.biz.ChannelType;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.dto.biz.CompanyServiceDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.service.CompanyService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.CustomerService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping("requireTotal")
@MenuTag(code = "requireTotal", name = "电销派单系统", sequence = 3, type = 0,icon="icon-headset_mic")
public class SaleCalculateController extends BaseController{
	
	@Resource
	private OrderService orderService;
	@Resource
	private CompanyService companyService;
	@Resource
	private CustomerHouseService customerHouseService;
	@Resource
	private CustomerService customerService;
	@Resource
	private ZoneService zoneService;
	
	public static final Integer HARD_TYPE_Id=1;//硬装需求的公司业务id 为1
	@MenuTag(code = "requireTotal-hard-set", name = "全部硬装需求", sequence = 1, type = 1 )
	@RequestMapping(value = "/hard-index")
	public String hardIndex(String operator, 
			String key, 
			Integer pageIndex, 
			Integer pageSize,
			String sort,
			String direction,
			String customerCode,
			String customerName,
			CustomerHouse selectParam,
			
			HttpServletRequest request, 
			Integer houseId, 
			String storeName,
			Map<String, Object> modal) {
		if (Constant.LIST.equals(operator)) {
				PageDTO<CustomerHouse> page=new PageDTO<CustomerHouse>();
				if(key!=null){
					selectParam.setHouseLocation(key);
				}
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setParam(selectParam);
				if(sort==null){sort="createDate";}
				page.setOrderBy(sort);
				if(direction==null){direction="asc";}
				page.setDirection(direction);
				PageDTO<List<CustomerHouseDTO>> result=customerHouseService.listByServiceAndPage
						(null, page, customerName, customerCode,HARD_TYPE_Id);
				modal.put(Constant.JSON, JsonUtils.object2json(result));
			
			return Constant.JSON;
		} else if ("one".equals(operator)) {
			Map<String, Object> result = new HashMap<String, Object>();
			CustomerHouse house = customerHouseService.get(houseId);
			if (house != null) {
				CustomerDTO old = customerService.get(house.getCustomerId());
				result.put("customerAge", old.getAge() != null ? old.getAge().getName() : "不详");

				String customerGender = "不详";
				if (old.getGender() != null) {
					if (old.getGender().equals("0")) {
						customerGender = "女";
					} else {
						customerGender = "男";
					}
				}
				result.put("customerGender", customerGender);

				Zone zone3 = zoneService.get(house.getZoneId());
				if (zone3 != null) {
					result.put("zone3", zone3.getName());
					Zone zone2 = zoneService.get(zone3.getParentId());
					if (zone2 != null) {
						result.put("zone2", zone2.getName());
						Zone zone1 = zoneService.get(zone2.getParentId());
						if (zone1 != null) {
							result.put("zone1", zone1.getName());
						}
					}
				}
				if (house.getIsNew() != null && house.getIsNew() == 0) {
					result.put("isNewInfo", "否");
				} else if (house.getIsNew() != null && house.getIsNew() == 1) {
					result.put("isNewInfo", "是");
				}

				if (house.getHasSoft() != null && house.getHasSoft() == 0) {
					result.put("hasSoftInfo", "否");
				} else if (house.getHasSoft() != null && house.getHasSoft() == 1) {
					result.put("hasSoftInfo", "是");
				}

				if (house.getGift() != null && house.getGift() == 0) {
					result.put("giftInfo", "否");
				} else if (house.getGift() != null && house.getGift() == 1) {
					result.put("giftInfo", "是");
				}

				if (house.getDesignType() != null && house.getDesignType() == 0) {
					result.put("designTypeInfo", "半包");
				} else if (house.getDesignType() != null && house.getDesignType() == 1) {
					result.put("designTypeInfo", "全包");
				}
			}

			PageDTO<CustomerHouse> page = new PageDTO<CustomerHouse>();

			page.setParam(house);
			page.setPageIndex(1);
			page.setPageSize(100);

			// 查询所有可接单的店铺
			PageDTO<List<CompanyServiceDTO>> datas = orderService.getCompanyService(page, house.getZoneId(), HARD_TYPE_Id,null);
			
			result.put("companyPage", datas);
			modal.put(Constant.JSON, JsonUtils.object2json(result));
			return Constant.JSON;
		} else if ("company".equals(operator)) {
			CustomerHouse house = customerHouseService.get(houseId);
			PageDTO<CustomerHouse> page = new PageDTO<CustomerHouse>();

			page.setParam(house);
			page.setPageIndex(1);
			page.setPageSize(100);
			PageDTO<List<CompanyServiceDTO>> datas =null;
			if(StringUtils.isEmpty(storeName)){
				datas = orderService.getCompanyService(page, house.getZoneId(), HARD_TYPE_Id,null);
			}else{
				// 查询所有可接单的店铺
				datas = orderService.getCompanyService(page, null, HARD_TYPE_Id,storeName);
			}

			modal.put(Constant.JSON, JsonUtils.object2json(datas));
			return Constant.JSON;
		}
		return "admin/all_hard_list";
	}
	
	/**
	 * 硬装需求
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "requireTotal-hard-set-close", name = "关闭需求", sequence = 2, type = 2, parentCode = "requireTotal-hard-set")
	@RequestMapping(value = "hard-close")
	@ResponseBody
	public JsonDTO hardClose(Integer houseId, HttpServletRequest request) {
		if (houseId == null) {
			return null;
		}
		JsonDTO json = new JsonDTO();
		CustomerHouse old = customerHouseService.get(houseId);
		if (old != null) {
			CustomerHouse house = new CustomerHouse();
			house.setHouseId(houseId);
			house.setStatus(3);
			house.setLog(old.getLog() + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
					+ getLoginAdminUser(request).getNickname() + "对需求进行了关闭操作" + "&#13;");
			try {
				customerHouseService.saveOrUpdate(house);
				json.setStatus(1).setMessage("关闭成功！");
			} catch (Exception e) {
				json.setStatus(0).setMessage("后台出现异常！");
				e.printStackTrace();
			}
		} else {
			json.setStatus(0).setMessage("数据不存在");
		}
		return json;
	}
	
	/**
	 * 硬装需求派单
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "requireTotal-hard-set-order", name = "硬装需求派单", sequence = 3, type = 2, parentCode = "requireTotal-hard-set")
	@RequestMapping(value = "hard-order")
	@ResponseBody
	public JsonDTO hardOrder(Integer houseId,Integer companyId,Integer price,HttpServletRequest request) {
		String log = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
				+ getLoginAdminUser(request).getNickname() + "创建" + "&#13;";
		CustomerHouse house=customerHouseService.get(houseId);
		CompanyDTO company=companyService.get(companyId);
		JsonDTO result=new JsonDTO();
		if(house!=null&&company!=null&&price!=null){
			house.setLog(house.getLog()+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
					+ getLoginAdminUser(request).getNickname() + "派单给" +company.getStoreName()+ "(硬装)&#13;");
			house.setStatus(2);//设为已派单
			customerHouseService.saveOrUpdate(house);
			Order order=new Order();
			order.setHouseId(houseId);
			order.setToId(companyId);
			order.setPrice(price);
			order.setType(1);//公司派单
			order.setTypeId(HARD_TYPE_Id);//硬装修
			order.setCreateDate(new Date());
			order.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
			order.setAcceptStatus(0);
			order.setLog(log);
			try {
				orderService.saveOrUpdate(order);
				
				result.setStatus(1).setMessage("派单成功").setData(customerHouseService.get(houseId));
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(0).setMessage("派单失败");
			}
			
		}else{
			result.setStatus(0).setMessage("数据异常！");
		}
		
		
		return result;
	}
	
	public static final Integer SOFT_TYPE_Id=2;//软装需求的公司业务id为1
	@MenuTag(code = "requireTotal-soft-set-index", name = "全部软装需求", sequence = 3, type = 1)
	@RequestMapping(value = "soft-index")
	public String softIndex(String operator, 
			String key, 
			Integer pageIndex, 
			Integer pageSize,
			String sort,
			String direction,
			String customerCode,
			String customerName,
			CustomerHouse selectParam,
			HttpServletRequest request, 
			Integer houseId, 
			String storeName,
			Map<String, Object> modal) {
		if (Constant.LIST.equals(operator)) {
				PageDTO<CustomerHouse> page=new PageDTO<CustomerHouse>();
				if(key!=null){
					selectParam.setHouseLocation(key);
				}
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setParam(selectParam);
				if(sort==null){sort="createDate";}
				page.setOrderBy(sort);
				if(direction==null){direction="asc";}
				page.setDirection(direction);
				PageDTO<List<CustomerHouseDTO>> result=customerHouseService.
						softListByServiceAndPage
						(null, page, customerName, customerCode,SOFT_TYPE_Id);
				modal.put(Constant.JSON, JsonUtils.object2json(result));
			return Constant.JSON;
		} else if ("one".equals(operator)) {
			Map<String, Object> result = new HashMap<String, Object>();
			CustomerHouse house = customerHouseService.get(houseId);
			if (house != null) {
				CustomerDTO old = customerService.get(house.getCustomerId());
				result.put("customerAge", old.getAge() != null ? old.getAge().getName() : "不详");

				String customerGender = "不详";
				if (old.getGender() != null) {
					if (old.getGender().equals("0")) {
						customerGender = "女";
					} else {
						customerGender = "男";
					}
				}
				result.put("customerGender", customerGender);

				Zone zone3 = zoneService.get(house.getZoneId());
				if (zone3 != null) {
					result.put("zone3", zone3.getName());
					Zone zone2 = zoneService.get(zone3.getParentId());
					if (zone2 != null) {
						result.put("zone2", zone2.getName());
						Zone zone1 = zoneService.get(zone2.getParentId());
						if (zone1 != null) {
							result.put("zone1", zone1.getName());
						}
					}
				}
				if (house.getIsNew() != null && house.getIsNew() == 0) {
					result.put("isNewInfo", "否");
				} else if (house.getIsNew() != null && house.getIsNew() == 1) {
					result.put("isNewInfo", "是");
				}

				if (house.getHasSoft() != null && house.getHasSoft() == 0) {
					result.put("hasSoftInfo", "否");
				} else if (house.getHasSoft() != null && house.getHasSoft() == 1) {
					result.put("hasSoftInfo", "是");
				}

				if (house.getGift() != null && house.getGift() == 0) {
					result.put("giftInfo", "否");
				} else if (house.getGift() != null && house.getGift() == 1) {
					result.put("giftInfo", "是");
				}

				if (house.getDesignType() != null && house.getDesignType() == 0) {
					result.put("designTypeInfo", "半包");
				} else if (house.getDesignType() != null && house.getDesignType() == 1) {
					result.put("designTypeInfo", "全包");
				}
			}

			PageDTO<CustomerHouse> page = new PageDTO<CustomerHouse>();

			page.setParam(house);
			page.setPageIndex(1);
			page.setPageSize(100);

			// 查询所有可接单的店铺
			PageDTO<List<CompanyServiceDTO>> datas = orderService.getCompanyService(page, house.getZoneId(), SOFT_TYPE_Id,null);
			
			result.put("companyPage", datas);
			modal.put(Constant.JSON, JsonUtils.object2json(result));
			return Constant.JSON;
		} else if ("company".equals(operator)) {
			CustomerHouse house = customerHouseService.get(houseId);
			PageDTO<CustomerHouse> page = new PageDTO<CustomerHouse>();

			page.setParam(house);
			page.setPageIndex(1);
			page.setPageSize(100);

			// 查询所有可接单的店铺
			PageDTO<List<CompanyServiceDTO>> datas = null;
			if(StringUtils.isEmpty(storeName)){
				datas = orderService.getCompanyService(page, house.getZoneId(), SOFT_TYPE_Id,null);
			}else{
				// 查询所有可接单的店铺
				datas = orderService.getCompanyService(page, null, SOFT_TYPE_Id,storeName);
			}

			modal.put(Constant.JSON, JsonUtils.object2json(datas));
			return Constant.JSON;
		}
		return "admin/all_soft_list";
	}

	/**
	 * 软装需求派单
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "requireTotal-soft-set-order", name = "软装需求派单", sequence = 3, type = 2, parentCode = "requireTotal-soft-set-index")
	@RequestMapping(value = "soft-order")
	@ResponseBody
	public JsonDTO softOrder(Integer houseId,Integer companyId,Integer price,HttpServletRequest request) {
		String log = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
				+ getLoginAdminUser(request).getNickname() + "创建" + "&#13;";
		CustomerHouse house=customerHouseService.get(houseId);
		CompanyDTO company=companyService.get(companyId);
		JsonDTO result=new JsonDTO();
		if(house!=null&&company!=null&&price!=null){
			house.setLog(house.getLog()+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
					+ getLoginAdminUser(request).getNickname() + "派单给" +company.getStoreName()+ "(软装)&#13;");
			house.setSoftStatus(1);//设为已派单
			customerHouseService.saveOrUpdate(house);
			Order order=new Order();
			order.setHouseId(houseId);
			order.setToId(companyId);
			order.setPrice(price);
			order.setType(1);//公司派单
			order.setTypeId(SOFT_TYPE_Id);//软装修
			order.setCreateDate(new Date());
			order.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
			order.setAcceptStatus(0);
			order.setLog(log);
			try {
				orderService.saveOrUpdate(order);
				result.setStatus(1).setMessage("派单成功").setData(customerHouseService.get(houseId));
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(0).setMessage("派单失败");
			}
			
		}else{
			result.setStatus(0).setMessage("数据异常！");
		}
		return result;
	}
	
	/**
	 * 硬装需求
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "myWork-soft-close", name = "关闭软装需求", sequence = 2, type = 2, parentCode = "myWork-hard-index")
	@RequestMapping(value = "soft-close")
	@ResponseBody
	public JsonDTO softClose(Integer houseId, HttpServletRequest request) {
		if (houseId == null) {
			return null;
		}
		JsonDTO json = new JsonDTO();
		CustomerHouse old = customerHouseService.get(houseId);
		if (old != null) {
			CustomerHouse house = new CustomerHouse();
			house.setHouseId(houseId);
			house.setSoftStatus(2);
			house.setLog(old.getLog() + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
					+ getLoginAdminUser(request).getNickname() + "对软装需求进行了关闭操作" + "&#13;");
			try {
				customerHouseService.saveOrUpdate(house);
				json.setStatus(1).setMessage("关闭成功！");
			} catch (Exception e) {
				json.setStatus(0).setMessage("后台出现异常！");
				e.printStackTrace();
			}
		} else {
			json.setStatus(0).setMessage("数据不存在");
		}
		return json;
	}
	
}
