package com.sf.qzm.controller.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.biz.ChannelDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AgeService;
import com.sf.qzm.service.BudgetService;
import com.sf.qzm.service.ChannelService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.CustomerService;
import com.sf.qzm.service.HouseStyleService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.manager.SendMsgResult;
import com.sf.qzm.util.manager.SmsUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping("myWork")
@MenuTag(code = "myWork", name = "我的任务", sequence = 4, type = 0,icon="icon-stars")
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
	@MenuTag(code = "myWork-customer-index", name = "我的客户", sequence = 1, type = 1 )
	@RequestMapping(value = "myWork-customer-index")
	public String  customerIndex(String operator,
			Customer customer,
			String key,
			Integer pageIndex,
			Integer pageSize,
			String sort,String direction,
			Map<String,Object> map,Integer customerId,
			Integer houseId,HttpServletRequest request){
		if(Constant.LIST.equals(operator)){
			
			if(pageSize!=null&&pageIndex!=null){//真分页
				PageDTO<Customer> page=new PageDTO<Customer>();
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setParam(customer);
				if(sort==null){sort="createDate";}
				page.setOrderBy(sort);
				if(direction==null){direction="asc";}
				page.setDirection(direction);
				customer.setServiceId(getLoginAdminUser(request).getAdminUserId());
				PageDTO<List<CustomerDTO>> datas=customerService.listByPage(page);
				//处理手机号码
				AdminUserDTO user= adminUserService.checkPower("myWork-customer-mobile-show", getLoginAdminUser(request).getAdminUserId());
				if(datas.getParam()!=null&&user==null){//没有权限
					hidePhone(datas.getParam());
				}
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			}else{//加分页
				List<CustomerDTO> datas=customerService.getByService(getLoginAdminUser(request).getAdminUserId());
				//处理手机号码
				AdminUserDTO user= adminUserService.checkPower("myWork-customer-mobile-show", getLoginAdminUser(request).getAdminUserId());
				if(datas!=null&&user==null){//没有权限
					hidePhone(datas);
				}
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			}
			return Constant.JSON;
			
		}else if("getHouse".equals(operator)){
			List<CustomerHouse> houses=customerHouseService.list(customerId);
			
			//初始化区域
			for(CustomerHouse house:houses){
				if(house.getZoneId()!=null){
					Zone zone=zoneService.get(house.getZoneId());
					house.setZoneParentId(zone.getParentId());
					Zone parent=zoneService.get(zone.getParentId());
					house.setZoneRootId(parent.getParentId());
				}
			}
			
			
			map.put(Constant.JSON, JsonUtils.object2json(houses));
			return Constant.JSON;
		}else if("publishHouse".equals(operator)){
			JsonDTO json=new JsonDTO();
			if(houseId==null){
				json.setStatus(0).setMessage("数据异常！请稍后再试");
			}else{
				CustomerHouse house=customerHouseService.get(houseId);

				if(house!=null){
					house.setStatus(1);//已发布
					try {
						String log=house.getLog()==null?"":house.getLog();
						house.setLog(log+new SimpleDateFormat("yyyy-MM-dd HH:mm").
								format(new Date())+" 由 "+getLoginAdminUser(request).getNickname()+" 从后台发布。&#13;");
						customerHouseService.saveOrUpdate(house);
						
						json.setStatus(1).setMessage("发布成功！");
					} catch (Exception e) {
						e.printStackTrace();
						json.setStatus(0).setMessage("更新失败");
					}
					
				}else{
					json.setStatus(0).setMessage("房产不存在！");
				}
				
			}
			map.put(Constant.JSON, JsonUtils.object2json(json));
			return Constant.JSON;
		}
		//查找所有用户渠道
		List<ChannelDTO> channel=channelService.all();
		map.put("channels", channel);
		//查找所有年龄
		List<Age> ages=ageService.all();
		map.put("ages", ages);
		//查找所有root区域
		List<Zone> zones=zoneService.root();
		map.put("zones", zones);
		//查找所有房型
		List<HouseStyle> styles=houseStyleService.all();
		map.put("styles", styles);
		//查找所有预算
		List<Budget> budgets=budgetService.all();
		map.put("budgets", budgets);
		
		//加载所有短信模版
		map.put("msgTemplate", SmsUtils.template);
				
		return "admin/myWork-customer-list";
	}
	
	
	@MenuTag(code = "myWork-customer-update", name = "更新用户", sequence = 1, type = 2,parentCode="myWork-customer-index")
	@RequestMapping(value = "/customer-update")
	@ResponseBody
	public JsonDTO customerUpdate(@RequestBody Customer customer,Map<String,Object> map,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		String name=customer.getName();
		String phone=customer.getPhone();
		if(StringUtils.isEmpty(name)){
			result.setStatus(0).setMessage("客户名字不能为空！");
			return result;
		}else if(StringUtils.isEmpty(phone)){
			result.setStatus(0).setMessage("客户电话不能为空！");
			return result;
		}
		//检测用户是否存在
		CustomerDTO old=customerService.get(customer.getCustomerId());
		CustomerDTO phoneRegix=customerService.get(phone);
		if(!old.getPhone().equals(phone)&&phoneRegix!=null){
			result.setStatus(0).setMessage("编号为"+old.getCustomerCode()+"的客户已经存在");
			return result;
		}
		//检测房产
		if(customer.getHouses()!=null){
			for(CustomerHouse temp:customer.getHouses()){
				Integer houseId=temp.getHouseId();
				//查看旧数据
				CustomerHouse oldHouse=customerHouseService.get(houseId);
				if(oldHouse==null){
					String log=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+" "+
							getLoginAdminUser(request).getNickname()+"添加"+ "&#13;";
					temp.setLog(log);
					temp.setStatus(0);//
					temp.setCreateDate(new Date());
					temp.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					temp.setCustomerId(customer.getCustomerId());
				}
				customerHouseService.saveOrUpdate(temp);
			}
		}
		
		try {
			customerService.saveOrUpdate(customer);
			result.setStatus(1).setMessage("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("修改时候，系统出现异常！");
		}
		
		return result;
	}
	
	@MenuTag(code = "myWork-customer-call", name = "呼叫客户", sequence = 3, type = 2,parentCode="myWork-customer-index")
	@RequestMapping(value = "/customer-call")
	@ResponseBody
	public JsonDTO customerCall(Integer customerId,HttpServletRequest request) {
		JsonDTO json=new JsonDTO();
		CustomerDTO customer= customerService.get(customerId);
		if(customer!=null){
			json.setStatus(1).setData(customer.getPhone());
		}else{
			json.setStatus(0);
		}
		return json;
	}
	
	@MenuTag(code = "myWork-customer-mobile-show", name = "显示号码", sequence = 5, type = 2,parentCode="myWork-customer-index")
	@RequestMapping(value = "/customer-mobile-show")
	public void mobileShow(Customer customer,Map<String,Object> map,HttpServletRequest request) {
		
	}
	
	@MenuTag(code = "myWork-customer-status", name = "状态切换", sequence = 3, type = 2,parentCode="myWork-customer-index")
	@RequestMapping(value = "/customer-status")
	@ResponseBody
	public JsonDTO customerStatus(Customer customer,Map<String,Object> map,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		
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
	
	@MenuTag(code = "myWork-customer-msg", name = "发送短信", sequence = 3, type = 2,parentCode="myWork-customer-index")
	@RequestMapping(value = "/customer-msg")
	@ResponseBody
	public JsonDTO customerMsg(@RequestParam("customerId") Integer customerId
			,@RequestParam("template") String template) {
		JsonDTO json=new JsonDTO();
		json.setStatus(0).setMessage("短信发送失败");
		CustomerDTO customerDTO=customerService.get(customerId);
		if(customerDTO!=null){
			SendMsgResult result=SmsUtils.sendMsg("", customerDTO.getPhone(), template);
			if(result.getSuccess()==1){
				json.setStatus(1).setMessage("短信发送成功！");
			}else{
				json.setStatus(0).setMessage(result.getMessage());
			}
		}
		
		return json;
	}
	
	
	
	
	/**
	 * 我的硬装需求
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "myWork-hard-index", name = "硬装需求管理", sequence = 2, type = 1 )
	@RequestMapping(value = "hard-index")
	public String  hardIndex(String operator,HttpServletRequest request,
			Map<String,Object> modal){
		if(Constant.LIST.equals(operator)){
			List<CustomerHouseDTO> houses=customerHouseService.listByService
					(getLoginAdminUser(request).getAdminUserId());
			modal.put(Constant.JSON, JsonUtils.object2json(houses));
			return Constant.JSON;
		}
		return "admin/my_hard_list";
	}
	
	/**
	 * 硬装需求
	 * @param operator
	 * @return
	 */
	@MenuTag(code = "myWork-hard-close", name = "关闭需求", sequence = 2, type = 2,parentCode="myWork-hard-index" )
	@RequestMapping(value = "hard-close")
	@ResponseBody
	public JsonDTO  hardClose(Integer houseId){
		if(houseId==null){return null;}
		JsonDTO json=new JsonDTO();
		CustomerHouse house=new CustomerHouse();
		house.setHouseId(houseId);
		house.setStatus(3);
		try {
			customerHouseService.saveOrUpdate(house);
			json.setStatus(1).setMessage("关闭成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("后台出现异常！");
			e.printStackTrace();
		}
		return json;
	}
	
	
	@MenuTag(code = "myWork-soft-index", name = "软装需求", sequence = 3, type = 1 )
	@RequestMapping(value = "soft-index")
	public void  softIndex(){
		
	}
	
	private void hidePhone(List<CustomerDTO> datas){
			for(CustomerDTO temp:datas){
				String phone=temp.getPhone();
				temp.setPhone(StringUtils.hideMobile(phone));
			}
	}
}
