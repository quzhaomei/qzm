package com.sf.qzm.controller.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sf.qzm.annotation.LoginTag;
import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.bean.biz.Customer;
import com.sf.qzm.bean.biz.CustomerHouse;
import com.sf.qzm.bean.biz.Order;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.bean.login.LoginOutMessage;
import com.sf.qzm.bean.message.Notice;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.controller.biz.MyOrderController;
import com.sf.qzm.controller.biz.MyWorkController;
import com.sf.qzm.controller.biz.OrderController;
import com.sf.qzm.controller.biz.SaleCalculateController;
import com.sf.qzm.controller.biz.UserRelationController;
import com.sf.qzm.dto.ImgUploadResultDTO;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.biz.CustomerDTO;
import com.sf.qzm.dto.biz.CustomerHouseDTO;
import com.sf.qzm.dto.menu.AutoMenuDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.CustomerService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.SystemSourceService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.PasswordUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping("welcome")
@LoginTag
public class WelcomeController extends BaseController {
	
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private AutoMenuService menuService;
	@Resource
	private OrderService orderService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<Notice> notices=new ArrayList<Notice>();
		Customer cuParam= new Customer();
		CustomerHouse huParam=new CustomerHouse();
		//查找总客户数
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			Notice notice=new Notice();
			notice.setTitle("总客户数");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		//查找新日新增客户数
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			cuParam.setCreateDate_start(new Date());
			cuParam.setCreateDate_end(new Date());
			Notice notice=new Notice();
			notice.setTitle("今日新增客户数");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		//未分配客户
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			cuParam=new Customer();
			cuParam.setStatus(1);
			Notice notice=new Notice();
			notice.setTitle("未分配客户");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		//今日客服以处理客户
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			cuParam=new Customer();
			cuParam.setCreateDate_start(new Date());
			cuParam.setCreateDate_end(new Date());
			cuParam.setStatus(2);
			int num2=customerService.count(cuParam);
			cuParam.setStatus(3);
			int num3=customerService.count(cuParam);
			cuParam.setStatus(4);
			int num4=customerService.count(cuParam);
			cuParam.setStatus(5);
			int num5=customerService.count(cuParam);
			Notice notice=new Notice();
			notice.setTitle("今日客服已处理客户");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(num2+num3+num4+num5+"");
			notices.add(notice);
		}
		//今日发布需求总数
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			huParam.setCreateDate_start(new Date());
			huParam.setCreateDate_end(new Date());
			int num1=customerHouseService.count(null, huParam, null);
			
			huParam.setHasSoft(1);//软装
			int num2=customerHouseService.count(null, huParam, null);
			Notice notice=new Notice();
			notice.setTitle("今日发布需求数");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(num1+num2+"");
			notices.add(notice);
		}
		
		//今日已派单需求总数
		if(hasPower(request, UserRelationController.CUSTOMER_INDEX_CODE)){
			huParam.setCreateDate_start(new Date());
			huParam.setCreateDate_end(new Date());
			huParam.setStatus(2);
			int num2=customerHouseService.count(null, huParam, null);
			huParam.setHasSoft(1);//软装
			huParam.setSoftStatus(1);
			int num4=customerHouseService.count(null, huParam, null);
			Notice notice=new Notice();
			notice.setTitle("今日已派单需求数");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(num2+num4+"");
			notices.add(notice);
		}
		
		Order orPram=new Order();
		orPram.setCreateDate_start(getYearFirst(Calendar.getInstance().get(Calendar.YEAR)));
		orPram.setCreateDate_end(getYearLast(Calendar.getInstance().get(Calendar.YEAR)));
		//年度总订单数
		if(hasPower(request, OrderController.ORDER_ALL)){
			Notice notice=new Notice();
			notice.setTitle("年度总订单数");
			notice.setUrl("order/order-all.htmls?ajaxTag_=1");
			notice.setMessage(orderService.count(orPram)+"");
			notices.add(notice);
		}
		//年度佣金总额
		if(hasPower(request, OrderController.ORDER_ALL)){
			Notice notice=new Notice();
			notice.setTitle("年度佣金总额");
			notice.setUrl("order/order-all.htmls?ajaxTag_=1");
			notice.setMessage(orderService.totalPrice(orPram, null,
					null, null)+"");
			notices.add(notice);
		}
		
		cuParam=new Customer();
		cuParam.setServiceId(getLoginAdminUser(request).getAdminUserId());
		//我负责的客户总数
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			Notice notice=new Notice();
			notice.setTitle("我负责的客户总数");
			notice.setUrl("myWork/myWork-customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		cuParam.setServiceDate_start(new Date());
		cuParam.setServiceDate_end(new Date());
		//今日新分配到的客户总数
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			Notice notice=new Notice();
			notice.setTitle("今日新分配到的客户");
			notice.setUrl("myWork/myWork-customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		//未回访客户
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			cuParam=new Customer();
			cuParam.setServiceId(getLoginAdminUser(request).getAdminUserId());
			cuParam.setStatus(2);
			Notice notice=new Notice();
			notice.setTitle("未回访客户");
			notice.setUrl("myWork/myWork-customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		//待跟进客户
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			cuParam=new Customer();
			cuParam.setServiceId(getLoginAdminUser(request).getAdminUserId());
			cuParam.setStatus(3);
			Notice notice=new Notice();
			notice.setTitle("待跟进客户");
			notice.setUrl("myWork/myWork-customer-index.htmls?ajaxTag_=1");
			notice.setMessage(customerService.count(cuParam)+"");
			notices.add(notice);
		}
		
		//我已发布的需求数
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			huParam=new CustomerHouse();
		int num1=customerHouseService.count(getLoginAdminUser(request).getAdminUserId(),
				huParam, null);
			huParam.setHasSoft(1);//软装
			int num2=customerHouseService.count(getLoginAdminUser(request).getAdminUserId(), huParam, null);
			Notice notice=new Notice();
			notice.setTitle("我已发布的需求数");
			notice.setUrl("sysCustomer/customer-index.htmls?ajaxTag_=1");
			notice.setMessage(num1+num2+"");
			notices.add(notice);
		
		}
		//我已派出的订单数
		if(hasPower(request, MyOrderController.MY_ORDER)){
			orPram=new Order();
			orPram.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
			int num1=orderService.count(orPram);
			Notice notice=new Notice();
			notice.setTitle("我已派出的订单数");
			notice.setUrl("myOrder/service-order.htmls?ajaxTag_=1");
			notice.setMessage(num1+"");
			notices.add(notice);
		}
		//已确认接单的订单数
		if(hasPower(request, MyOrderController.MY_ORDER)){
			orPram.setAcceptStatus(1);
			int num1=orderService.count(orPram);
			Notice notice=new Notice();
			notice.setTitle("已确认接单的订单数");
			notice.setUrl("myOrder/service-order.htmls?ajaxTag_=1");
			notice.setMessage(num1+"");
			notices.add(notice);
		}
		
		//待处理客户
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			cuParam=new Customer();
			cuParam.setServiceId(getLoginAdminUser(request).getAdminUserId());
			cuParam.setStatus(2);
				PageDTO<Customer> page = new PageDTO<Customer>();
				page.setPageIndex(1);
				page.setPageSize(5);
				page.setParam(cuParam);
				page.setOrderBy("createDate");
				page.setDirection("desc");
				PageDTO<List<CustomerDTO>> datas = customerService.listByPage(page);
				// 处理手机号码
				AdminUserDTO user = adminUserService.checkPower("myWork-customer-mobile-show",
						getLoginAdminUser(request).getAdminUserId());
				if (datas.getParam() != null && user == null) {// 没有权限
					hidePhone(datas.getParam());
				}
				model.addAttribute("needDo", datas);
		}
		
		//待发布需求的房产
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			huParam=new CustomerHouse();
			huParam.setStatus(0);
			PageDTO<CustomerHouse> page=new PageDTO<CustomerHouse>();
			page.setPageIndex(1);
			page.setPageSize(5);
			page.setParam(huParam);
			page.setOrderBy("createDate");
			page.setDirection("desc");
			
			PageDTO<List<CustomerHouseDTO>> result=customerHouseService.listAllByServiceAndPage
					(getLoginAdminUser(request).getAdminUserId(), page, null, null,MyWorkController.HARD_TYPE_Id);
			model.addAttribute("needHouse", result);
		}
		
		//待派单需求
		if(hasPower(request, MyWorkController.MY_WORK_INDEX)){
			huParam=new CustomerHouse();
			huParam.setStatus(1);//待派单
			PageDTO<CustomerHouse> page=new PageDTO<CustomerHouse>();
			page.setPageIndex(1);
			page.setPageSize(3);
			page.setParam(huParam);
			page.setOrderBy("createDate");
			page.setDirection("desc");
			PageDTO<List<CustomerHouseDTO>> result=customerHouseService.listByServiceAndPage
					(getLoginAdminUser(request).getAdminUserId(), page, null, null,MyWorkController.HARD_TYPE_Id);
			int hardSize=result.getParam()!=null?5-result.getParam().size():5;
			huParam.setStatus(null);//待派单
			huParam.setHasSoft(1);
			huParam.setSoftStatus(0);
			PageDTO<CustomerHouse> pageSoft=new PageDTO<CustomerHouse>();
			pageSoft.setPageIndex(1);
			pageSoft.setPageSize(hardSize);
			pageSoft.setParam(huParam);
			PageDTO<List<CustomerHouseDTO>> result2=customerHouseService.softListByServiceAndPage
					(getLoginAdminUser(request).getAdminUserId(), pageSoft, null, null,MyWorkController.HARD_TYPE_Id);
			
			PageDTO<List<CustomerHouseDTO>> resultTotal=new PageDTO<List<CustomerHouseDTO>>();
			List<CustomerHouseDTO> datas=new ArrayList<CustomerHouseDTO>();
			for(CustomerHouseDTO temp:result.getParam()){
				temp.setHouseId(null);//标注为硬装
				datas.add(temp);
			}
			for(CustomerHouseDTO temp:result2.getParam()){
				datas.add(temp);
			}
			
			Collections.sort(datas, new Comparator<CustomerHouseDTO>() {

				@Override
				public int compare(CustomerHouseDTO o1, CustomerHouseDTO o2) {
					return (int) (o2.getCreateDate()-o1.getCreateDate());
				}
			});
			
			resultTotal.setPageIndex(1);
			resultTotal.setPageSize(5);
			resultTotal.setParam(datas);
			resultTotal.setCount(result.getCount()+result2.getCount());
			
			model.addAttribute("needRequired", resultTotal);
			
		}
		
		model.addAttribute("notices", notices);
		
		model.addAttribute("loginUser", request.getSession().getAttribute(Constant.ADMIN_USER_SESSION));
		return "admin/welcome";
	}
	
	
    public static Date getYearFirst(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        return currYearFirst;  
    }  
      
    /** 
     * 获取某年最后一天日期 
     * @param year 年份 
     * @return Date 
     */  
    public static Date getYearLast(int year){  
        Calendar calendar = Calendar.getInstance();  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
        Date currYearLast = calendar.getTime();  
          
        return currYearLast;  
    } 
    
    @RequestMapping(value = "/update_self")
    @ResponseBody
    public JsonDTO updateSelf(String avatar,String nickname,String phone,String password,
    		HttpSession session) {
    	JsonDTO json=new JsonDTO();
    	AdminUserDTO user= (AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
    	AdminUser update=new AdminUser();
    	update.setAdminUserId(user.getAdminUserId());
    	update.setAvatar(avatar);
    	update.setNickname(nickname);
    	update.setPhone(phone);
    	if(!StringUtils.isEmpty(password)){
    		update.setPassword(PasswordUtils.MD5(password));
    	}
    	try {
			adminUserService.saveOrUpdate(update);
			json.setStatus(1).setMessage("更新成功！");
			
			if(avatar!=null){
				user.setAvatar(avatar);
			}
			if(nickname!=null){
				user.setNickname(nickname);
			}
			if(phone!=null){
				user.setPhone(phone);
			}
			
		} catch (Exception e) {
			json.setStatus(0).setMessage("更新失败！");
			e.printStackTrace();
		}
    	
    	return json;
    }
    
    
	@Resource
	private CustomerService customerService;
	@Resource
	private CustomerHouseService customerHouseService;
	
	public static final String MY_CUSTOMER_URL="myWork/myWork-customer-index.htmls?ajaxTag_=1";
	public static final String HARD_URL="myWork/hard-index.htmls?ajaxTag_=1";
	public static final String SOFT_URL="myWork/soft-index.htmls?ajaxTag_=1";
	@RequestMapping(value = "/dailyMessage")
	@ResponseBody
	public List<Notice> dailyMessage(HttpServletRequest request) {
		List<Notice> list=new ArrayList<Notice>();
		//查找新分配
		Customer customer=new Customer();
		customer.setStatus(2);//等待回访
		customer.setServiceId(getLoginAdminUser(request).getAdminUserId());
		int newTask=customerService.count(customer);
		
		if(newTask>0){
			Notice notice=new Notice().setMessage("您有"+newTask+"新分配的用户需要回访")
					.setUrl(MY_CUSTOMER_URL);
			list.add(notice);
			
		}
		
		customer.setStatus(3);//待跟进
		int requireTask=customerService.count(customer);
		if(requireTask>0){
			Notice notice=new Notice().setMessage("您有"+requireTask+"个待跟进库客户需要处理")
					.setUrl(MY_CUSTOMER_URL);
			list.add(notice);
		}
		
		//房产发布
		CustomerHouse customerHouse=new CustomerHouse();
		customerHouse.setStatus(0);
		int all=customerHouseService.allCount(customerHouse,getLoginAdminUser(request).getAdminUserId());
		if(all>0){
			Notice notice=new Notice().setMessage("您有"+all+"房产需求未发布")
					.setUrl(MY_CUSTOMER_URL);
			list.add(notice);
		}
		
		//硬装需求派单
		customerHouse.setStatus(1);
		int hard=customerHouseService.count(getLoginAdminUser(request).getAdminUserId(), customerHouse, SaleCalculateController.HARD_TYPE_Id);
		if(hard>0){
			Notice notice=new Notice().setMessage("您有"+hard+"个硬装需求尚未派单")
					.setUrl(HARD_URL);
			list.add(notice);
		}
		
		//软装需求派单
		customerHouse.setStatus(null);
		customerHouse.setSoftStatus(0);
		customerHouse.setHasSoft(1);
		int soft=customerHouseService.count(getLoginAdminUser(request).getAdminUserId(), customerHouse, SaleCalculateController.SOFT_TYPE_Id);
		if(soft>0){
			Notice notice=new Notice().setMessage("您有"+soft+"个软装需求尚未派单")
					.setUrl(SOFT_URL);
			list.add(notice);
		}
		
		return list;
	}
	
	
	/**
	 * 查询是否有此权限
	 * @param powerCode
	 * @return
	 */
	@RequestMapping(value = "/checkPower")
	@ResponseBody
	public JsonDTO checkPower(HttpServletRequest request, String powerCode,HttpSession session) {
		String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
		JsonDTO json=new JsonDTO();
		AdminUserDTO user= (AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		if(user.getPhone().equals(godPhone)|| user.getMenuCodeMap().get(powerCode)!=null){
			json.setStatus(1);//1为有权限，0表示没有权限
		}else{
			json.setStatus(0);//1为有权限，0表示没有权限
		}
		return json;
	}
	
	private boolean hasPower(HttpServletRequest request,String powerCode){
		String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
		AdminUserDTO user= (AdminUserDTO) request.getSession().getAttribute(Constant.ADMIN_USER_SESSION);
		if(user.getPhone().equals(godPhone)|| user.getMenuCodeMap().get(powerCode)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return json字符串
	 * logo:{image:"images/logo.svg",title:"我的后台管理",url:"url"},
		user:{name:"游客",avatar:"images/avatar.jpg"},
		menu:[{
		    	  icon:"icon-cog",
		    	  title:"系统设置",
		    	  url:"",
		    	  sequence:1,
		    	  childMenu:[
		    	        {title:"通用设定",url:"",sequence:1},
		    	        {title:"内容模块管理",url:"",sequence:2,},
		    	        {title:"角色及权限分配",url:"",sequence:3,},
		    	        {title:"后台菜单",url:"",sequence:4}
		    	   ]
		      },
		      {icon:"icon-users",title:"用户管理",url:"",sequence:2,childMenu:[]}
		      ],
		 menu_:[
		        {icon:"icon-earth",title:"站点信息设置",url:"",sequence:1,childMenu:[]},
		        ]
		       }
	 */
	@ResponseBody
	@RequestMapping(value="/menu-info")
	public Map<String,Object> menuInfo(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, Model model) {
		AdminUserDTO loginUser=(AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		Map<String,Object> result=new HashMap<String, Object>();
		//模拟数据
		Map<String,String> user=new HashMap<String, String>();
		user.put("name", loginUser.getNickname());
		
		user.put("avatar", loginUser.getAvatar()==null?"/static/images/avatar.jpg":loginUser.getAvatar());
		result.put("user", user);
		
		Map<String,String> logo=new HashMap<String, String>();
		logo.put("image", "/static/images/logo-admin.png");
		logo.put("title", SfContextUtils.getSystemSourceByKey("title"));
		logo.put("url", "welcome/index.htmls");
		
		result.put("logo", logo);
		//根据用户角色查找菜单
		List<AutoMenuDTO> menuList=menuService.getAdminNavMenu(loginUser.getAdminUserId());
		String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
		
		if(godPhone.equals(loginUser.getPhone())){//超级账号
			menuList=menuService.getAdminNavMenu();
		}
		
		//拆分
		List<AutoMenuDTO> menu=new ArrayList<AutoMenuDTO>();
		List<AutoMenuDTO> menu_=new ArrayList<AutoMenuDTO>();
		for(AutoMenuDTO temp:menuList){
			if(temp.getExtend()!=null&&temp.getExtend()==1){
				menu_.add(temp);
			}else{
				menu.add(temp);
			}
		}
		
		result.put("menu", menu);
		result.put("menu_", menu_);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/sss_check")
	public LoginOutMessage checkLoginStatus(HttpSession session){
		LoginOutMessage	loginOut=new LoginOutMessage();
			loginOut.setType(1);//正常状态
			return loginOut;
	}
	
	@Resource
	private ZoneService zoneService;
	
	@ResponseBody
	@RequestMapping(value="/getZone")
	public List<Zone> getChildZone(Integer id){
		List<Zone> zones=zoneService.list(id);
		return zones;
	}
	
	@RequestMapping(value="/allZone")
	public String allZone(String varible,Map<String,Object> model){
		List<Map<String,Object>> zones=new ArrayList<Map<String, Object>>();
		List<Zone> roots=zoneService.root();
		for(Zone temp:roots){
			Map<String,Object> tempRoot=new HashMap<String, Object>();
			tempRoot.put("zoneId", temp.getZoneId());
			tempRoot.put("name", temp.getName());
			tempRoot.put("children", initChildren(temp.getZoneId()));
			zones.add(tempRoot);
		}
		model.put("varible", varible);
		model.put("value", JsonUtils.object2json(zones));
		return "script";
	}
	
	private List<Map<String,Object>> initChildren(Integer parentId){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Zone> children=zoneService.list(parentId);
		if(children!=null&&children.size()>0){
			for(Zone temp:children){
				Map<String,Object> tempRoot=new HashMap<String, Object>();
				tempRoot.put("zoneId", temp.getZoneId());
				tempRoot.put("name", temp.getName());
				tempRoot.put("children", initChildren(temp.getZoneId()));
				result.add(tempRoot);
			}
		}
		return result;
	}
	
	@Resource
	private SystemSourceService sourceService;
	private String tomcat_house=System.getProperty("catalina.base");//tomcat根目录
	//上传图片
	@RequestMapping(value = "/uploadSource/{mkdirs}")
	@ResponseBody
	public ImgUploadResultDTO uploadSource(@PathVariable(value="mkdirs") String mkdirs,
				@RequestParam(value = "file") MultipartFile file, Model model) {
		String rootPath=sourceService.getByKey("img_path");//获取全局配置，图片根目录
			String orName=file.getOriginalFilename();
			String houzui="";
			if(orName.lastIndexOf(".")!=-1){
				houzui=orName.substring(orName.lastIndexOf("."));
			}
			File parent = new File("/"+tomcat_house+"/"+rootPath+"/"+mkdirs );
			if (!parent.exists()) {
				parent.mkdirs();
			}
			
			String filename = UUID.randomUUID()+houzui;
			
			File target = new File(parent, filename);
			// 如果大于200K，则进行压缩
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImgUploadResultDTO result=new ImgUploadResultDTO();
			result.setError(0);
			result.setUrl("/"+rootPath+"/"+mkdirs+"/"+filename);
			// 回传结果
			return result;
		}
	
	
	

	private void hidePhone(List<CustomerDTO> datas) {
		for (CustomerDTO temp : datas) {
			String phone = temp.getPhone();
			temp.setPhone(StringUtils.hideMobile(phone));
		}
	}
}
