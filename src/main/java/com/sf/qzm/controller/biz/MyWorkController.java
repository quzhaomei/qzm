package com.sf.qzm.controller.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.Customer;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.biz.ChannelDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.dto.biz.CompanyServiceDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.dto.biz.MessageTemplateDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AgeService;
import com.sf.qzm.service.BudgetService;
import com.sf.qzm.service.ChannelService;
import com.sf.qzm.service.CompanyService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.CustomerService;
import com.sf.qzm.service.HouseStyleService;
import com.sf.qzm.service.MessageTemplateService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.manager.SendMsgResult;
import com.sf.qzm.util.manager.SmsUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping("myWork")
@MenuTag(code = "myWork", name = "我的任务", sequence = 4, type = 0, icon = "icon-stars")
public class MyWorkController extends BaseController {
	@Resource
	private CustomerService customerService;

	@Resource
	private ChannelService channelService;

	@Resource
	private AgeService ageService;

	@Resource
	private ZoneService zoneService;

	@Resource
	private HouseStyleService houseStyleService;

	@Resource
	private BudgetService budgetService;

	@Resource
	private AdminUserService adminUserService;

	@Resource
	private CustomerHouseService customerHouseService;
	
	@Resource
	private MessageTemplateService templateService;

	public static final String MY_WORK_INDEX="myWork-customer-index";
	@MenuTag(code = MY_WORK_INDEX, name = "我的客户", sequence = 1, type = 1)
	@RequestMapping(value = "myWork-customer-index")
	public String customerIndex(String operator, Customer customer, String key, Integer pageIndex, Integer pageSize,
			String sort, String direction, Map<String, Object> map, Integer customerId, Integer houseId,
			HttpServletRequest request) {
		if (Constant.LIST.equals(operator)) {

			if (pageSize != null && pageIndex != null) {// 真分页
				PageDTO<Customer> page = new PageDTO<Customer>();
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setParam(customer);
				if (sort == null) {
					sort = "createDate";
				}
				page.setOrderBy(sort);
				if (direction == null) {
					direction = "asc";
				}
				page.setDirection(direction);
				customer.setServiceId(getLoginAdminUser(request).getAdminUserId());
				PageDTO<List<CustomerDTO>> datas = customerService.listByPage(page);
				// 处理手机号码
				AdminUserDTO user = adminUserService.checkPower("myWork-customer-mobile-show",
						getLoginAdminUser(request).getAdminUserId());
				if (datas.getParam() != null && user == null) {// 没有权限
					hidePhone(datas.getParam());
				}
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			} else {// 加分页
				List<CustomerDTO> datas = customerService.getByService(getLoginAdminUser(request).getAdminUserId());
				// 处理手机号码
				AdminUserDTO user = adminUserService.checkPower("myWork-customer-mobile-show",
						getLoginAdminUser(request).getAdminUserId());
				if (datas != null && user == null) {// 没有权限
					hidePhone(datas);
				}
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			}
			return Constant.JSON;

		} else if ("getHouse".equals(operator)) {
			List<CustomerHouse> houses = customerHouseService.list(customerId);

			// 初始化区域
			for (CustomerHouse house : houses) {
				if (house.getZoneId() != null) {
					Zone zone = zoneService.get(house.getZoneId());
					house.setZoneParentId(zone.getParentId());
					Zone parent = zoneService.get(zone.getParentId());
					house.setZoneRootId(parent.getParentId());
				}
			}

			map.put(Constant.JSON, JsonUtils.object2json(houses));
			return Constant.JSON;
		} else if ("publishHouse".equals(operator)) {
			JsonDTO json = new JsonDTO();
			if (houseId == null) {
				json.setStatus(0).setMessage("数据异常！请稍后再试");
			} else {
				CustomerHouse house = customerHouseService.get(houseId);

				if (house != null) {
					if(house.noReadyMsg()==null){
						house.setStatus(1);// 已发布
						try {
							String log = house.getLog() == null ? "" : house.getLog();
							house.setLog(log + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
									+ getLoginAdminUser(request).getNickname() + " 从后台发布。&#13;");
							customerHouseService.saveOrUpdate(house);
							
							json.setStatus(1).setMessage("发布成功！");
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage("更新失败");
						}
					}else{
						json.setStatus(0).setMessage(house.noReadyMsg());
					}

				} else {
					json.setStatus(0).setMessage("房产不存在！");
				}

			}
			map.put(Constant.JSON, JsonUtils.object2json(json));
			return Constant.JSON;
		}else if("deleteHouse".equals(operator)){//删除
			JsonDTO json=new JsonDTO();
			if(houseId==null){
				json.setStatus(0).setMessage("数据异常！请稍后再试");
			}else{
				CustomerHouse house=customerHouseService.get(houseId);
				if(house!=null){//发布前验证数据
						house.setIsDelete(1);//删除tag
						try {
							String log=house.getLog()==null?"":house.getLog();
							house.setLog(log+new SimpleDateFormat("yyyy-MM-dd HH:mm").
									format(new Date())+" 由 "+getLoginAdminUser(request).getNickname()+" 从后台删除。&#13;");
							customerHouseService.saveOrUpdate(house);
							customerService.fresh();
							CustomerDTO newCu=customerService.get(house.getCustomerId());
							json.setStatus(1).setMessage("删除成功！").setData(newCu);
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage("删除失败");
						}
					
				}else{
					json.setStatus(0).setMessage("房产不存在！");
				}
			}
			map.put(Constant.JSON, JsonUtils.object2json(json));
			return Constant.JSON;
		}
		// 查找所有用户渠道
		List<ChannelDTO> channel = channelService.all();
		map.put("channels", channel);
		// 查找所有年龄
		List<Age> ages = ageService.all();
		map.put("ages", ages);
		// 查找所有root区域
		List<Zone> zones = zoneService.root();
		map.put("zones", zones);
		// 查找所有房型
		List<HouseStyle> styles = houseStyleService.all();
		map.put("styles", styles);
		// 查找所有预算
		List<Budget> budgets = budgetService.all();
		map.put("budgets", budgets);

		// 加载所有短信模版
		map.put("msgTemplate", templateService.allLive());

		return "admin/myWork-customer-list";
	}

	@MenuTag(code = "myWork-customer-update", name = "更新用户", sequence = 1, type = 2, parentCode = MY_WORK_INDEX)
	@RequestMapping(value = "/customer-update")
	@ResponseBody
	public JsonDTO customerUpdate(@RequestBody Customer customer, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO result = new JsonDTO();
	
		// 检测房产
		if (customer.getHouses() != null) {
			for (CustomerHouse temp : customer.getHouses()) {
				Integer houseId = temp.getHouseId();
				// 查看旧数据
				CustomerHouse oldHouse = customerHouseService.get(houseId);
				if (oldHouse == null) {
					String log = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " "
							+ getLoginAdminUser(request).getNickname() + "添加" + "&#13;";
					temp.setLog(log);
					temp.setStatus(0);//
					temp.setCreateDate(new Date());
					temp.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					temp.setCustomerId(customer.getCustomerId());
				}else{
					Integer status=temp.getStatus();
					if(status!=null&&status==1){
						String log = oldHouse.getLog() == null ? "" : oldHouse.getLog();
						temp.setLog(log + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()) + " 由 "
								+ getLoginAdminUser(request).getNickname() + " 从后台发布。&#13;");
					}
				}
				customerHouseService.saveOrUpdate(temp);
			}
		}

		try {
			customerService.saveOrUpdate(customer);
			CustomerDTO newCu=customerService.get(customer.getCustomerId());
			result.setStatus(1).setMessage("修改成功!").setData(newCu);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("修改时候，系统出现异常！");
		}

		return result;
	}

	@MenuTag(code = "myWork-customer-call", name = "呼叫客户", sequence = 3, type = 2, parentCode = MY_WORK_INDEX)
	@RequestMapping(value = "/customer-call")
	@ResponseBody
	public JsonDTO customerCall(Integer customerId, HttpServletRequest request) {
		JsonDTO json = new JsonDTO();
		CustomerDTO customer = customerService.get(customerId);
		if (customer != null) {
			json.setStatus(1).setData(customer.getPhone());
		} else {
			json.setStatus(0);
		}
		return json;
	}

	@MenuTag(code = "myWork-customer-mobile-show", name = "显示号码", sequence = 5, type = 2, parentCode = MY_WORK_INDEX)
	@RequestMapping(value = "/customer-mobile-show")
	public void mobileShow(Customer customer, Map<String, Object> map, HttpServletRequest request) {

	}

	@MenuTag(code = "myWork-customer-status", name = "状态切换", sequence = 3, type = 2, parentCode = MY_WORK_INDEX)
	@RequestMapping(value = "/customer-status")
	@ResponseBody
	public JsonDTO customerStatus(Customer customer, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO result = new JsonDTO();

		try {
			customer.setFileDate(new Date());
			customerService.saveOrUpdate(customer);
			result.setStatus(1).setMessage("状态更改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("更改时候，系统出现异常！");
		}

		return result;
	}

	@MenuTag(code = "myWork-customer-msg", name = "发送短信", sequence = 3, type = 2, parentCode = MY_WORK_INDEX)
	@RequestMapping(value = "/customer-msg")
	@ResponseBody
	public JsonDTO customerMsg(@RequestParam("customerId") Integer customerId
			,@RequestParam("templateId") Integer templateId) {
		JsonDTO json=new JsonDTO();
		json.setStatus(0).setMessage("短信发送失败");
		CustomerDTO customerDTO=customerService.get(customerId);
		 MessageTemplateDTO templateDTO= templateService.get(templateId);
		if(customerDTO!=null&&templateDTO!=null){
			SendMsgResult result=SmsUtils.sendMsg(customerDTO.getName(), 
					customerDTO.getPhone(),
					templateDTO.getCode(),templateDTO.getSign());
			if(result.getSuccess()==1){
				json.setStatus(1).setMessage("短信发送成功！");
			}else{
				json.setStatus(0).setMessage(result.getMessage());
			}
		}	
	
		return json;
	}

	@Resource
	private OrderService orderService;
	@Resource
	private CompanyService companyService;

	/**
	 * 我的硬装需求
	 * 
	 * @param operator
	 * @return
	 */
	public static final Integer HARD_TYPE_Id=1;//硬装需求的公司业务id 为1
	@MenuTag(code = "myWork-hard-index", name = "硬装需求管理", sequence = 2, type = 1)
	@RequestMapping(value = "hard-index")
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
						(getLoginAdminUser(request).getAdminUserId(), page, customerName, customerCode,HARD_TYPE_Id);
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
		return "admin/my_hard_list";
	}

	/**
	 * 硬装需求
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "myWork-hard-close", name = "关闭硬装需求", sequence = 2, type = 2, parentCode = "myWork-hard-index")
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
					+ getLoginAdminUser(request).getNickname() + "对硬装需求进行了关闭操作" + "&#13;");
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
	@MenuTag(code = "myWork-hard-order", name = "硬装需求派单", sequence = 3, type = 2, parentCode = "myWork-hard-index")
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
	@MenuTag(code = "myWork-soft-index", name = "软装需求管理", sequence = 3, type = 1)
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
						(getLoginAdminUser(request).getAdminUserId(), page, customerName, customerCode,SOFT_TYPE_Id);
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
		return "admin/my_soft_list";
	}

	/**
	 * 软装需求派单
	 * 
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "myWork-soft-order", name = "软装需求派单", sequence = 3, type = 2, parentCode = "myWork-soft-index")
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
			house.setSoftStatus(1);//设为软装已派单
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
	
	
	
	
	private void hidePhone(List<CustomerDTO> datas) {
		for (CustomerDTO temp : datas) {
			String phone = temp.getPhone();
			temp.setPhone(StringUtils.hideMobile(phone));
		}
	}
}
