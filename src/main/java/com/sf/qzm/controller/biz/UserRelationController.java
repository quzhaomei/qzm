package com.sf.qzm.controller.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.Channel;
import com.sf.qzm.bean.biz.ChannelType;
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
import com.sf.qzm.dto.biz.ChannelTypeDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.dto.biz.MessageTemplateDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AgeService;
import com.sf.qzm.service.BudgetService;
import com.sf.qzm.service.ChannelService;
import com.sf.qzm.service.ChannelTypeService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.CustomerService;
import com.sf.qzm.service.HouseStyleService;
import com.sf.qzm.service.MessageTemplateService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.manager.SendMsgResult;
import com.sf.qzm.util.manager.SmsUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.RandomUtils;
import com.sf.qzm.util.other.StringUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
@RequestMapping("sysCustomer")
@MenuTag(code = "sysCustomer", name = "客户关系系统", sequence = 2, type = 0,icon="icon-person_pin")
public class UserRelationController extends BaseController{
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
	private CustomerHouseService customerHouseService;
	
	@Resource
	private AdminUserService adminUserService;
	
	@Resource
	private MessageTemplateService templateService;
	
	public static final String CUSTOMER_INDEX_CODE="sysCustomer-customer-index";
	@MenuTag(code = CUSTOMER_INDEX_CODE, name = "客户数据管理", sequence = 1, type = 1 )
	@RequestMapping(value = "/customer-index")
	public String customerIndex(String operator,
			Customer customer,
			String key,
			Integer pageIndex,
			Integer pageSize,Map<String,Object> map,Integer customerId,
			String sort,String direction,
			Integer houseId,HttpServletRequest request
			) {
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
				PageDTO<List<CustomerDTO>> datas=customerService.listByPage(page);
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			}else{//加分页
				List<CustomerDTO> datas=customerService.all();
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

				if(house!=null){//发布前验证数据
					if(house.noReadyMsg()==null){
						house.setStatus(1);//已发布
						try {
							String log=house.getLog()==null?"":house.getLog();
							house.setLog(log+new SimpleDateFormat("yyyy-MM-dd HH:mm").
									format(new Date())+" 由 "+getLoginAdminUser(request).getNickname()+" 从后台发布。&#13;");
							customerHouseService.saveOrUpdate(house);
							json.setStatus(1).setMessage("发布成功！");
							customerService.fresh();
						} catch (Exception e) {
							e.printStackTrace();
							json.setStatus(0).setMessage("发布失败");
						}
					}else{
						json.setStatus(0).setMessage(house.noReadyMsg());
					}
					
				}else{
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
						CustomerDTO newCu=customerService.get(customer.getCustomerId());
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
		//查找所有有分单权限的用户
		List<AdminUserDTO> users=adminUserService.getByPower("myWork-customer-index");//我的任务页面
		map.put("services", users);
		//加载所有短信模版
		map.put("msgTemplate", templateService.allLive());
		
		return "admin/customer-list";
	}
	
	
	@MenuTag(code = "sysCustomer-customer-download", name = "客户数据下载", sequence = 8, type = 2 ,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/customer-download")
	public String customerDownload(
			Customer customer,
			String key,
			String sort,String direction,
			HttpServletResponse response
			) {
		PageDTO<Customer> page=new PageDTO<Customer>();
		page.setParam(customer);
		if(sort==null){sort="createDate";}
		page.setOrderBy(sort);
		if(direction==null){direction="asc";}
		page.setDirection(direction);
		List<CustomerDTO> datas=customerService.download(page);
		WritableWorkbook wwb = null;
		String fileName = "客户数据.xls";
		File file=new File(fileName);
		try {
			wwb = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		WritableSheet ws = wwb.createSheet("列表一", 0);// 建立工作簿
		// 写表头
		jxl.write.Label label1 = new jxl.write.Label(0, 0, "客户编号");
		jxl.write.Label label2 = new jxl.write.Label(1, 0, "客户姓名");
		jxl.write.Label label3 = new jxl.write.Label(2, 0, "手机");
		jxl.write.Label label4 = new jxl.write.Label(3, 0, "状态");
		jxl.write.Label label5 = new jxl.write.Label(4, 0, "备注");
		jxl.write.Label label6 = new jxl.write.Label(5, 0, "渠道");
		jxl.write.Label label7 = new jxl.write.Label(6, 0, "录入日期");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			ws.addCell(label1);
			ws.addCell(label2);
			ws.addCell(label3);
			ws.addCell(label4);
			ws.addCell(label5);
			ws.addCell(label6);
			ws.addCell(label7);
			for(int index=0;index<datas.size();index++){
				CustomerDTO temp=datas.get(index);
				Integer customerCode=temp.getCustomerCode();
				String customerName=temp.getName();
				String phone=temp.getPhone();
				Integer status=temp.getStatus();//，1-待分配，2-等待回访，3-待跟进，4-关闭,5－已回访
				String statusInfo="";
				switch (status) {
				case 1:
					statusInfo="待分配";
					break;
				case 2:
					statusInfo="等待回访";
					break;
				case 3:
					statusInfo="待跟进";
					break;
				case 4:
					statusInfo="关闭";
					break;
				case 5:
					statusInfo="已回访";
					break;
				default:
					statusInfo="待分配";
					break;
				}
				
				
				String info=temp.getNextcallInfo();
				ChannelDTO channel=temp.getChannel();
				Long createDate=temp.getCreateDate();
				
				jxl.write.Label temp0 = new jxl.write.Label(0, index + 1,customerCode+"");//
				jxl.write.Label temp1 = new jxl.write.Label(1, index + 1,customerName);// 
//				jxl.write.Label temp2 = new jxl.write.Label(2, index + 1,StringUtils.hideMobile(phone));//
				jxl.write.Label temp2 = new jxl.write.Label(2, index + 1,phone);//
				jxl.write.Label temp3 = new jxl.write.Label(3, index + 1,statusInfo);//
				jxl.write.Label temp4 = new jxl.write.Label(4, index + 1,info);//
				jxl.write.Label temp5 = new jxl.write.Label(5, index + 1,channel.getName());//
				jxl.write.Label temp6 = new jxl.write.Label(6, index + 1,format.format(new Date(createDate)));//
				ws.addCell(temp0);
				ws.addCell(temp1);
				ws.addCell(temp2);
				ws.addCell(temp3);
				ws.addCell(temp4);
				ws.addCell(temp5);
				ws.addCell(temp6);
			}
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String contentType = "application/x-download";
			response.setContentType(contentType);
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			
			ServletOutputStream out = response.getOutputStream();
			
			byte[] bytes = new byte[0xffff];
			InputStream is = new FileInputStream(new File(fileName));
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			
			is.close();
			out.flush();
		} catch (Exception e) {
		}finally {
			if(file.exists()){
				file.delete();
			}
		}
		return null;
	}
	
	//&#13;
	@MenuTag(code = "sysCustomer-customer-save", name = "添加用户", sequence = 1, type = 2,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/customer-save")
	@ResponseBody
	public JsonDTO customerSave(Customer customer,Map<String,Object> map,HttpServletRequest request) {
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
		if(StringUtils.isEmpty(customer.getInfo())){
			customer.setInfo(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+"由"+getLoginAdminUser(request).getNickname()+"从后台录入。");
		}
		//检测用户是否存在
		CustomerDTO old=customerService.get(phone);
		if(old!=null){
			result.setStatus(0).setMessage("编号为"+old.getCustomerCode()+"的客户已经存在");
			return result;
		}
		customer.setCreateDate(new Date());
		customer.setStatus(1);
		customer.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		//生成用户编码
		customer.setCustomerCode(customerService.nextCustomerCode());
		
		try {
			customerService.saveOrUpdate(customer);
			result.setStatus(1).setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("保存时候，系统出现异常！");
		}
		
		return result;
	}
	@MenuTag(code = "sysCustomer-customer-call", name = "呼叫客户", sequence = 3, type = 2,parentCode=CUSTOMER_INDEX_CODE)
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
	
	@MenuTag(code = "sysCustomer-customer-status", name = "状态切换", sequence = 3, type = 2,parentCode=CUSTOMER_INDEX_CODE)
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
	
	/**
	 * 
	 */
	//&#13;
	@MenuTag(code = "sysCustomer-customer-service", name = "分单", sequence = 1, type = 2,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/customer-service")
	@ResponseBody
	public JsonDTO customerService(@RequestParam(required=true) Integer customerId,@RequestParam(required=true)Integer serviceId) {
		JsonDTO json=new JsonDTO();
		CustomerDTO customerDTO=customerService.get(customerId);
		if(customerDTO==null){
			json.setStatus(0).setMessage("该客户不存在！");
		}else{
			Customer param=new Customer();
			param.setCustomerId(customerId);
			param.setServiceId(serviceId);
			param.setServiceDate(new Date());
			param.setStatus(2);//已分单
			try {
				customerService.saveOrUpdate(param);
				json.setStatus(1).setMessage("分单成功！");
			} catch (Exception e) {
				json.setStatus(0).setMessage("分单失败");
			}
		}
		return json;
	}
	@MenuTag(code = "sysCustomer-customer-msg", name = "发送短信", sequence = 3, type = 2,parentCode=CUSTOMER_INDEX_CODE)
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
	@MenuTag(code = "sysCustomer-customer-batch-msg", name = "批量发送短信", sequence = 4, type = 2,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/batch-customer-msg")
	@ResponseBody
	public JsonDTO customerBatchMsg(@RequestParam("ids[]") Integer[] ids
			,@RequestParam("templateId") Integer templateId) {
		JsonDTO json=new JsonDTO();
		 MessageTemplateDTO templateDTO= templateService.get(templateId);
		if(ids.length<200&&templateDTO!=null){
			List<CustomerDTO> customers=customerService.getByIds(ids);
			StringBuilder phone=new StringBuilder();
			for(CustomerDTO temp:customers){
				phone.append(temp.getPhone()+",");
			}
			String phones="";
			if(phone.length()>1){
				 phones=phone.toString().substring(0, phone.length()-1);
			}
			SendMsgResult result=SmsUtils.sendMsg("", phones, 
					templateDTO.getCode(),templateDTO.getSign());
			if(result.getSuccess()==1){
				json.setStatus(1).setMessage("短信发送成功！");
			}else{
				json.setStatus(0).setMessage(result.getMessage());
			}
		}else{
			json.setStatus(0).setMessage("一次最多只能发送200人！");
		}
		return json;
	}
	
	@MenuTag(code = "sysCustomer-customer-batch-service", name = "批量分单", sequence = 1, type = 2,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/customer-batch-service")
	@ResponseBody
	public JsonDTO customerBatchService(@RequestParam(value="customerIds[]",required=true ) Integer[]customerIds,Integer serviceId) {
		JsonDTO json=new JsonDTO();
		for(int index=0;index<customerIds.length;index++){
			Integer customerId=customerIds[index];
			CustomerDTO customerDTO=customerService.get(customerId);
			if(customerDTO!=null){
				Customer param=new Customer();
				param.setCustomerId(customerId);
				param.setServiceId(serviceId);
				param.setStatus(2);//已分单
				try {
					customerService.saveOrUpdate(param);
				} catch (Exception e) {
				}
			}
			
		}
		json.setStatus(1).setMessage("操作成功！");
		return json;
	}
	
	/**
	 * 	批量上传
	 */
	@MenuTag(code = "sysCustomer-batchUpload-customer-save", name = "批量添加", 
			sequence = 1, type = 2,parentCode=CUSTOMER_INDEX_CODE)
	@RequestMapping(value = "/customer-batchUpload-save")
	public String batchUpload(Map<String,Object> map,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required=true) String operator,
			@RequestParam(value = "file", required = false) MultipartFile excell,
			Integer channelId
			) {
		if ("upload".equals(operator)) {
			try {
				Workbook rwb = Workbook.getWorkbook(excell.getInputStream());
				Sheet rst = rwb.getSheet(0);
				// 获取Sheet表中所包含的总列数
				List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
				// 获取Sheet表中所包含的总行数
				int rsRows = rst.getRows();
				Map<String,String> repeatMap=new HashMap<String, String>();
				for (int row = 1; row < rsRows; row++) {
					Map<String,Object> temp=new HashMap<String, Object>();
					String username=rst.getCell(0, row).getContents();
					temp.put("username",username);
					String phone=rst.getCell(1, row).getContents();
					temp.put("phone",phone);
					String houseLocation=rst.getCell(2, row).getContents();
					temp.put("houseLocation",houseLocation);
					//判断是否存在 exist
					if(phone!=null){
						String hasRepat=repeatMap.put(phone, "true");
						if(customerService.get(phone)!=null||hasRepat!=null){
							temp.put("exist", true);
						}
					}
					datas.add(temp);
				}
				JsonDTO json = new JsonDTO();
				json.setStatus(1).setData(datas);
				map.put(Constant.JSON, JsonUtils.object2json(datas));
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return Constant.JSON;
		}else if("downloadRepeat".equals(operator)){//下载重复数据
			WritableWorkbook wwb = null;
			String fileName = "重复数据.xls";
			File file=new File(fileName);
			try {
				wwb = Workbook.createWorkbook(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] usernames=request.getParameterValues("username");
			String[] phone=request.getParameterValues("phone");
			String[] houseLocation= request.getParameterValues("houseLocation");
			WritableSheet ws = wwb.createSheet("列表一", 0);// 建立工作簿
			// 写表头
			jxl.write.Label label1 = new jxl.write.Label(0, 0, "客户");
			jxl.write.Label label2 = new jxl.write.Label(1, 0, "手机");
			jxl.write.Label label3 = new jxl.write.Label(2, 0, "重复描述");
			
			jxl.write.Label label4 = new jxl.write.Label(3, 0, "房产需求详细地址");
			jxl.write.Label label5 = new jxl.write.Label(4, 0, "渠道");
			jxl.write.Label label6 = new jxl.write.Label(5, 0, "录入日期");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				ws.addCell(label1);
				ws.addCell(label2);
				ws.addCell(label3);
				ws.addCell(label4);
				ws.addCell(label5);
				ws.addCell(label6);
				for(int index=0;index<usernames.length&&index<phone.length&&index<houseLocation.length;index++){
					String username_temp=usernames[index];
					String phone_temp=phone[index];
					String repeatType="";
					String houseLocation_temp=houseLocation[index];
					String channelName_temp="";
					String inTime="";
					CustomerDTO old=customerService.get(phone_temp.trim());
					if(old==null){
						repeatType="本次上传重复";
					}else{
						repeatType="系统已有该用户记录";
						channelName_temp=old.getChannel().getName();
						List<CustomerHouseDTO> houses=old.getHouses();
						if(houses!=null){
							for(CustomerHouseDTO temp:houses){
								houseLocation_temp+=temp.getHouseLocation()+" ";
							}
						}
						inTime=format.format(new Date(old.getCreateDate()));
						username_temp=old.getName();
					}
					
					
					
					jxl.write.Label temp0 = new jxl.write.Label(0, index + 1,username_temp);//
					jxl.write.Label temp1 = new jxl.write.Label(1, index + 1,phone_temp);// 
					jxl.write.Label temp2 = new jxl.write.Label(2, index + 1,repeatType);//
					jxl.write.Label temp3 = new jxl.write.Label(3, index + 1,houseLocation_temp);//
					jxl.write.Label temp4 = new jxl.write.Label(4, index + 1,channelName_temp);//
					jxl.write.Label temp5 = new jxl.write.Label(5, index + 1,inTime);//
					ws.addCell(temp0);
					ws.addCell(temp1);
					ws.addCell(temp2);
					ws.addCell(temp3);
					ws.addCell(temp4);
					ws.addCell(temp5);
				}
				wwb.write();
				// 关闭Excel工作薄对象
				wwb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				String contentType = "application/x-download";
				response.setContentType(contentType);
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
				
				ServletOutputStream out = response.getOutputStream();
				
				byte[] bytes = new byte[0xffff];
				InputStream is = new FileInputStream(new File(fileName));
				int b = 0;
				while ((b = is.read(bytes, 0, 0xffff)) > 0) {
					out.write(bytes, 0, b);
				}
				
				is.close();
				out.flush();
			} catch (Exception e) {
			}finally {
				if(file.exists()){
					file.delete();
				}
			}
		}else if("uploadDate".equals(operator)){//上传数据
			JsonDTO json=new JsonDTO();
			
			String[] usernames=request.getParameterValues("username");
			String[] phone=request.getParameterValues("phone");
			String[] houseLocation= request.getParameterValues("houseLocation");
			for(int index=0;index<usernames.length&&index<phone.length
					&&index<houseLocation.length;index++){
				//查看是否重复
				CustomerDTO old=customerService.get(phone[index]);
				Integer customerId=null;
				if(old==null){//保存客户
					Customer customer=new Customer();
					customer.setName(usernames[index]);
					if(phone[index]!=null&&phone[index].length()>50){
						customer.setPhone(phone[index].substring(0, 50));
					}else{
						customer.setPhone(phone[index]);
					}
					customer.setCustomerCode(customerService.nextCustomerCode());
					customer.setChannelId(channelId);
					customer.setStatus(1);
					customer.setCreateDate(new Date());
					customer.setIntegration(0);
					customer.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					customer.setInfo("由"+getLoginAdminUser(request).getNickname()+"从后台批量导入.");
					customerService.saveOrUpdate(customer);
					customerId=customer.getCustomerId();
				}else{
					
					customerId=old.getCustomerId();
					
					Customer customer=new Customer();
					customer.setCustomerId(customerId);
					customer.setName(usernames[index]);
					if(phone[index]!=null&&phone[index].length()>50){
						customer.setPhone(phone[index].substring(0, 50));
					}else{
						customer.setPhone(phone[index]);
					}
					customer.setCustomerCode(customerService.nextCustomerCode());
					customer.setChannelId(channelId);
					customer.setStatus(1);
					customer.setCreateDate(new Date());
					customer.setIntegration(0);
					customer.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					customer.setInfo("由"+getLoginAdminUser(request).getNickname()+"从后台批量导入.");
					customerService.saveOrUpdate(customer);
					
				}
				//保存房产
				if(!StringUtils.isEmpty(houseLocation[index])){
					CustomerHouse house=new CustomerHouse();
					house.setCustomerId(customerId);
					house.setCreateDate(new Date());
					house.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
					house.setStatus(0);
					house.setHouseLocation(houseLocation[index]);
					house.setLog(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())+" "+
					getLoginAdminUser(request).getNickname()+"从后台批量导入"+ "&#13;");
					customerHouseService.saveOrUpdate(house);
				}
				
			}
			json.setStatus(1).setMessage("上传成功！");
			map.put(Constant.JSON, JsonUtils.object2json(json));
			return Constant.JSON;
		}
		return null;
	}
	
	@MenuTag(code = "sysCustomer-customer-update", name = "更新用户", sequence = 1, type = 2,parentCode=CUSTOMER_INDEX_CODE)
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
			result.setStatus(0).setMessage("号码为"+phone+"的客户已经存在");
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
	
	
	//渠道
	@Resource
	private ChannelTypeService channelTypeService;
	/**渠道类别开始 */

	
	
	/**渠道类结束 */
	
	
	@MenuTag(code = "source-channel", name = "来源渠道管理", sequence = 4, type = 1 )
	@RequestMapping(value = "/channel")
	public String channel(String operator,Map<String,Object> map) {
		if(Constant.LIST.equals(operator)){
			List<ChannelDTO> channels=channelService.all();
			map.put(Constant.JSON, JsonUtils.object2json(channels));
			return Constant.JSON;
		}else if("type_list".equals(operator)){
			List<ChannelTypeDTO> channels=channelTypeService.all();
			map.put(Constant.JSON, JsonUtils.object2json(channels));
			return Constant.JSON;
		}
		//
		List<ChannelTypeDTO> channelTypes=channelTypeService.all();
		map.put("channelTypes", channelTypes);
		return "admin/channel-list";
	}
	
	@MenuTag(code = "source-channel-status", name = "渠道状态管理", sequence = 6, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/channel/{channelId}/status")
	@ResponseBody
	public JsonDTO channelStatus(@PathVariable("channelId") Integer channelId) {
		JsonDTO result=new JsonDTO();
		 ChannelDTO old= channelService.get(channelId);
		 if(old==null){
			 result.setStatus(0).setMessage("渠道不存在！");
		 }else{
			 Integer status=old.getStatus();
			 String message;
			 if(status==0){
				 status=1;
				 message="激活成功！";
			 }else{
				 status=0;
				 message="渠道已经冻结";
			 }
			 Channel param=new Channel();
			 param.setChannelId(channelId);
			 param.setStatus(status);
			 try {
				 channelService.saveOrUpdate(param);
				 result.setStatus(1).setMessage(message);
			} catch (Exception e) {
				result.setStatus(0).setMessage("后台出现异常，请联系管理员！");
			}
		 }
		return result;
	}
	
	@MenuTag(code = "source-channel-save", name = "新增渠道", sequence = 5, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/channel-save")
	@ResponseBody
	public JsonDTO channelSave(Channel channel,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		String name=channel.getName();
		String code=channel.getCode();
		if(StringUtils.isEmpty(name)){
			result.setStatus(0).setMessage("渠道名字不能为空！");
		}else{//保存
			if(!StringUtils.isEmpty(code)){
				ChannelDTO old= channelService.get(code);
				if(old!=null){//返回
					result.setStatus(0).setMessage("渠道编码已经存在了");
					return result;
				}
			}else{//生成渠道编码
				ChannelDTO old=null;
				do {
					code=RandomUtils.genertorRandomCode(8);
					old= channelService.get(code);
				} while (old!=null);
			}
			
				channel.setCode(code);
				 channel.setCreateDate(new Date());
				 channel.setStatus(1);
				 channel.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				 
				 try {
					 channelService.saveOrUpdate(channel);
					 result.setStatus(1).setMessage("保存成功");
				} catch (Exception e) {
					result.setStatus(0).setMessage("保存渠道过程中，后台出现异常");
			 }
		}
		return result;
	}
	@MenuTag(code = "source-channel-update", name = "渠道更新", sequence = 6, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/channel-update")
	@ResponseBody
	public JsonDTO channelUpdate(Channel channel,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		String name=channel.getName();
		String code=channel.getCode();
		if(StringUtils.isEmpty(name)){
			result.setStatus(0).setMessage("渠道名字不能为空！");
		}else if(StringUtils.isEmpty(code)){
			result.setStatus(0).setMessage("渠道编码不能为空！");
		}else{//保存
			ChannelDTO old= channelService.get(code);
			if(old!=null&&!old.getCode().equals(code)){//返回
				result.setStatus(0).setMessage("渠道编码已经存在了");
					return result;
				}
			try {
				channelService.saveOrUpdate(channel);
				result.setStatus(1).setMessage("更新成功");
			} catch (Exception e) {
				result.setStatus(0).setMessage("更新渠道过程中，后台出现异常");
			}
		}
		return result;
	}
	@MenuTag(code = "source-channel-delete", name = "删除渠道", sequence = 7, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/{channelId}/channel-delete")
	@ResponseBody
	public JsonDTO channelDelete(@PathVariable("channelId") Integer channelId,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		ChannelDTO aim=channelService.get(channelId);
		if(aim==null){
			result.setStatus(0).setMessage("该渠道不存在！");
		}else{
			channelService.delete(channelId);
			result.setStatus(1).setMessage("该渠道删除成功！");
		}
		return result;
	}
	
	@MenuTag(code = "source-channel-type-save", name = "新增渠道类型", sequence = 5, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/channel-type-save")
	@ResponseBody
	public JsonDTO channelTypeSave(ChannelType channel,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		String name=channel.getName();
		if(StringUtils.isEmpty(name)){
			result.setStatus(0).setMessage("渠道类型名字不能为空！");
		}else{//保存
			channel.setCreateDate(new Date());
			channel.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
			channel.setIsDelete(0);
			try {
				channelTypeService.saveOrUpdate(channel);
				channelService.clear();//刷新缓存
				result.setStatus(1).setMessage("保存成功");
			} catch (Exception e) {
				result.setStatus(0).setMessage("保存过程中系统出现异常！");
			}
		}
		return result;
	}
	
	@MenuTag(code = "source-channel-type-update", name = "修改渠道类型", sequence = 5, type = 2 ,parentCode="source-channel")
	@RequestMapping(value = "/channel-type-update")
	@ResponseBody
	public JsonDTO channelTypeUpdate(ChannelType channel,HttpServletRequest request) {
		JsonDTO result=new JsonDTO();
		String name=channel.getName();
		if(StringUtils.isEmpty(name)){
			result.setStatus(0).setMessage("渠道类型名字不能为空！");
		}else{//保存
			channel.setCreateDate(new Date());
			channel.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
			channel.setIsDelete(0);
			try {
				channelTypeService.saveOrUpdate(channel);
				channelService.clear();//刷新缓存
				result.setStatus(1).setMessage("修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.setStatus(0).setMessage("修改过程中系统出现异常！");
			}
		}
		return result;
	}
	
}
