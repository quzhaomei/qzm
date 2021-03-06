package com.sf.qzm.controller.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.admin.AdminUser;
import com.sf.qzm.bean.admin.RoleManager;
import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.bean.biz.MessageTemplate;
import com.sf.qzm.bean.constant.Age;
import com.sf.qzm.bean.constant.Budget;
import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.dto.biz.MessageTemplateDTO;
import com.sf.qzm.dto.menu.PowerMenuDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AgeService;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.service.BudgetService;
import com.sf.qzm.service.CompanyTypeService;
import com.sf.qzm.service.HouseStyleService;
import com.sf.qzm.service.MessageTemplateService;
import com.sf.qzm.service.RoleManagerService;
import com.sf.qzm.service.RoleToMenusService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.PasswordUtils;
import com.sf.qzm.util.other.StringUtils;

@Controller
@RequestMapping("system")
@MenuTag(code = "system", name = "全局系统管理", sequence = 0, type = 0,icon="icon-settings")
public class SystemSetController extends BaseController{
	@Resource
	private RoleManagerService roleService;
	@Resource
	private AutoMenuService menuService;
	@Resource
	private RoleToMenusService roleToMenusService;
	@Resource
	private AdminUserService adminUserService;
	
	/**
	@MenuTag(code = "system-sysSet", name = "系统设置及维护", sequence = 23, type = 1 )
	@RequestMapping(value = "/sysSet")
	public void sysSet() {
		
	}
	 * 
	 */
	
	/**
	 * {menuId:"1",name:"系统模块设置",
	     				childMenu:[
	     				{menuId:"2",name:"通用设定",status:0,
	     				childMenu:[{menuId:"6",name:"通用设定设置",status:0},{menuId:"7",name:"自定义键值对",status:0}]},
	     				{menuId:"3",name:"内容模块管理",status:1,
	     				childMenu:[{menuId:"7",name:"编辑内容模块",status:1},{menuId:"8",name:"切换模块状态",status:1}]},
	     				{menuId:"4",name:"角色权限管理",status:1,
	     				childMenu:[{menuId:"9",name:"添加角色",status:0},{menuId:"10",name:"编辑角色信息",status:1}]},
	     				{menuId:"5",name:"后台菜单",status:1,
	     				childMenu:[{menuId:"9",name:"编辑菜单",status:1}]}
	     				]}
	 * @return
	 */
	@MenuTag(code = "system-power", name = "角色权限分配", sequence =2, type = 1 )
	@RequestMapping(value = "/power")
	public String power(String operator, Model model,Integer roleId) {
		
		if("json".equals(operator)){
			List<RoleManagerDTO> roles=roleService.getAllRole();
			model.addAttribute("json", JsonUtils.object2json(roles));
			return "json";
		}else if("power".equals(operator)){
			if(roleId==null){return "";}
			model.addAttribute("json", JsonUtils.object2json(roleToMenusService.getMenuTreeByRoleId(roleId)));
			return "json";
		}
		return "admin/role-menus";
	}
	
	@MenuTag(code = "system-role-update", name = "更新角色", sequence = 2, type = 2,parentCode="system-power" )
	@RequestMapping(value = "/roleUpdate/{roleId}")
	@ResponseBody
	public JsonDTO updaterole(String roleName, @PathVariable Integer roleId) {
		JsonDTO result=new JsonDTO();
		RoleManagerDTO role=roleService.getById(roleId);
		if(role!=null){
			try {
				roleService.setRoleNameById(roleId, roleName);
				result.setStatus(1).setMessage("更新成功");
			} catch (Exception e) {
				result.setStatus(0).setMessage("更新失败");
				e.printStackTrace();
			}
		}else{
			result.setStatus(0).setMessage("角色并不存在");
		}
		return result;
	}
	
	@MenuTag(code = "system-role-add", name = "增加角色", sequence = 1, type = 2,parentCode="system-power" )
	@RequestMapping(value = "/roleAdd")
	@ResponseBody
	public JsonDTO roleAdd(String roleName) {
		JsonDTO result=new JsonDTO();
		RoleManager role=new RoleManager();
		role.setCreateDate(new Date());
		role.setStatus(1);
		role.setRoleName(roleName);
			try {
				roleService.saveOrUpdate(role);
				result.setStatus(1).setMessage("添加成功");
			} catch (Exception e) {
				result.setStatus(0).setMessage("添加失败");
				e.printStackTrace();
			}
		return result;
	}
	
	@MenuTag(code = "system-role-status", name = "角色状态", sequence = 3, type = 2,parentCode="system-power" )
	@RequestMapping(value = "/roleStatus")
	@ResponseBody
	public JsonDTO roleStatus(Integer roleId) {
		JsonDTO result=new JsonDTO();
		RoleManagerDTO role=roleService.getById(roleId);
		if(role==null){
			result.setStatus(0).setMessage("角色不存在！");
		}else{
		RoleManager update=new RoleManager();
		update.setRoleId(roleId);
		update.setStatus((role.getStatus()+1)%2);
		try {
			roleService.saveOrUpdate(update);
			result.setStatus(1).setMessage("更新成功");
		} catch (Exception e) {
			result.setStatus(0).setMessage("更新失败");
			e.printStackTrace();
		}
		}
		return result;
	}
	
	@MenuTag(code = "system-power-update", name = "更新权限", sequence = 10, type = 2,parentCode="system-power" )
	@RequestMapping(value = "/powerUpdate/{roleId}")
	@ResponseBody
	public JsonDTO updatepower(@RequestBody PowerMenuDTO[] data,@PathVariable Integer roleId) {
		JsonDTO result=new JsonDTO();
		List<Integer> menuIds=new ArrayList<Integer>();
		for(PowerMenuDTO temp:data){
			if(temp.getMenuId()!=null&&menuService.getById(temp.getMenuId())!=null){
				menuIds.add(temp.getMenuId());
			}
		}
		try {
			roleToMenusService.setPowerForRole(roleId, menuIds);
			menuService.clear();
			result.setStatus(1).setMessage("设置成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("设置成功!");
		}
		return result;
	}
	
	
	@RequestMapping("/userIndex")
	@MenuTag(code = "userManager-index", name = "用户管理", sequence =1, type = 1)
	public String index(String operator,Model model){
		if("list".equals(operator)){
			List<AdminUserDTO> user=adminUserService.getAllUser();
			model.addAttribute("json", JsonUtils.object2json(user));
			return "json";
		}
		//查找所有角色
		List<RoleManagerDTO> roles=roleService.getAllRole();
		model.addAttribute("roles", roles);
		
		
		return "admin/user-list";
	}
	
	@RequestMapping("/saveUser")
	@MenuTag(code = "userManager-save", name = "新添用户", sequence =0, type = 2,parentCode="userManager-index")
	@ResponseBody
	public JsonDTO save(HttpServletRequest request, AdminUser adminUser,Integer roleId){
		JsonDTO json=new JsonDTO();
		//数据检测
		String loginname=adminUser.getLoginname();
		String phone=adminUser.getPhone();
		String nickname=adminUser.getNickname();
		if(nickname==null){
			json.setStatus(0).setMessage("昵称不能为空");
		}else if(loginname==null){
			json.setStatus(0).setMessage("用户名不能为空");
		}else if(phone==null){
			json.setStatus(0).setMessage("手机不能为空");
		}else if(!phone.matches("1[3|4|5|6|7|8]\\d{9}")){
			json.setStatus(0).setMessage("手机格式不正确");
		}else if(roleId==null){
			json.setStatus(0).setMessage("请选择角色");
		}else{
			RoleManagerDTO role=roleService.getById(roleId);
			AdminUserDTO old=adminUserService.getUser(loginname);
			if(role==null){
				json.setStatus(0).setMessage("该角色不存在");
			}else if(old!=null){
				json.setStatus(0).setMessage("该用户名已经被使用");
			}else{	
				//检测用户是否存在
				List<Integer> roleIds=new ArrayList<Integer>();
				roleIds.add(roleId);
				//进行保存操作
				String password=adminUser.getPassword();
				if(password==null){password=phone;}
				adminUser.setCreateDate(new Date());
				adminUser.setStatus(1);
				adminUser.setPassword(PasswordUtils.MD5(password));
				adminUser.setRoleIds(roleIds);
				adminUser.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
				try {
					adminUserService.saveOrUpdate(adminUser);
					json.setStatus(1).setMessage("保存成功");
				} catch (Exception e) {
					json.setStatus(0).setMessage("保存用户过程中出现异常，请联系管路员");
					e.printStackTrace();
				}
			}
		}
		return json;
	}
	
	@RequestMapping("/updateUser")
	@MenuTag(code = "userManager-update", name = "更新用户", sequence =1, type = 2,parentCode="userManager-index")
	@ResponseBody
	public JsonDTO update(AdminUser adminUser, @RequestParam(value="newRoleIds[]") Integer[] roleIds){
		JsonDTO json=new JsonDTO();
		
		String phone=adminUser.getPhone();
		String nickname=adminUser.getNickname();
		
		if(nickname==null){
			json.setStatus(0).setMessage("昵称不能为空");
		}else if(phone==null){
			json.setStatus(0).setMessage("手机不能为空");
		}else if(!phone.matches("1[3|4|5|6|7|8]\\d{9}")){
			json.setStatus(0).setMessage("手机格式不正确");
		}else if(roleIds==null||roleIds.length==0){
			json.setStatus(0).setMessage("请选择角色");
		}else{
			List<Integer> roleIdList=new ArrayList<Integer>();
			Collections.addAll(roleIdList, roleIds);
			
			if(StringUtils.isEmpty(adminUser.getPassword())){//设置密码
				adminUser.setPassword(null);
			}else{
				adminUser.setPassword(PasswordUtils.MD5(adminUser.getPassword()));
			}
			
			try {
				adminUser.setRoleIds(roleIdList);
				adminUserService.saveOrUpdate(adminUser);
				json.setStatus(1).setMessage("更新成功！");
			} catch (Exception e) {
				json.setStatus(0).setMessage("更新过程中出现问题");
				e.printStackTrace();
			}
		}
		return json;
	}
	@RequestMapping("/{userId}/deleteUser")
	@MenuTag(code = "userManager-delete", name = "删除用户", sequence =2, type = 2,parentCode="userManager-index")
	@ResponseBody
	public JsonDTO delete(@PathVariable("userId") Integer userId){
		JsonDTO json=new JsonDTO();
		AdminUserDTO user=adminUserService.getById(userId);
		if(user==null){
			json.setStatus(0).setMessage("用户不存在");
		}else{
			try {
				adminUserService.delete(userId);
				json.setStatus(1).setMessage("用户已被删除");
			} catch (Exception e) {
				json.setStatus(0).setMessage("用户删除过程中系统出现异常");
			}
		}
		return json;
	}
	
	@RequestMapping("/{userId}/statusUser")
	@MenuTag(code = "userManager-status", name = "切换状态", sequence =2, type = 2,parentCode="userManager-index")
	@ResponseBody
	public JsonDTO status(@PathVariable("userId") Integer userId){
		JsonDTO json=new JsonDTO();
		AdminUserDTO user=adminUserService.getById(userId);
		if(user==null){
			json.setStatus(0).setMessage("用户不存在");
		}else{
			Integer status=user.getStatus();
			String message="";
			if(status==1){
				status=0;
				message="用户已被冻结";
			}else{
				status=1;
				message="用户已被激活";
			}
			AdminUser updateParam=new AdminUser();
			updateParam.setStatus(status);
			updateParam.setAdminUserId(userId);
			try {
				adminUserService.saveOrUpdate(updateParam);
				json.setStatus(1).setMessage(message);
			} catch (Exception e) {
				json.setStatus(0).setMessage("更新过程中系统出现异常");
			}
		}
		return json;
	}
	

	/*** 年龄段开始 */
	@Resource
	private AgeService ageService;

	@MenuTag(code = "constant-age-index", name = "年龄段管理", sequence = 10, type = 1)
	@RequestMapping(value = "/age-index")
	public String ageIndex(String operator, Map<String, Object> map) {
		if (Constant.LIST.equals(operator)) {
			List<Age> ages = ageService.all();
			map.put(Constant.JSON, JsonUtils.object2json(ages));
			return Constant.JSON;
		}
		return "admin/age-list";
	}

	@MenuTag(code = "constant-age-save", name = "添加年龄段", sequence = 1, type = 2, parentCode = "constant-age-index")
	@RequestMapping(value = "/age-save")
	@ResponseBody
	public JsonDTO ageSave(Age age) {
		JsonDTO json = new JsonDTO();
		try {
			ageService.saveOrUpdate(age);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-age-edit", name = "修改年龄段", sequence = 2, type = 2, parentCode = "constant-age-index")
	@RequestMapping(value = "/age-edit")
	@ResponseBody
	public JsonDTO ageedit(Age age) {
		JsonDTO json = new JsonDTO();
		try {
			ageService.saveOrUpdate(age);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-age-delete", name = "修改年龄段", sequence = 2, type = 2, parentCode = "constant-age-index")
	@RequestMapping(value = "{ageId}/age-delete")
	@ResponseBody
	public JsonDTO ageDelete(@PathVariable("ageId") Integer ageId) {
		JsonDTO json = new JsonDTO();
		try {
			ageService.delete(ageId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}

	/*** 年龄段结束 */

	/** 预算开始 */
	@Resource
	private BudgetService budgetService;

	@MenuTag(code = "constant-budget-index", name = "预算管理", sequence = 12, type = 1)
	@RequestMapping(value = "/budget-index")
	public String budgetIndex(String operator, Map<String, Object> map) {
		if (Constant.LIST.equals(operator)) {
			List<Budget> budget = budgetService.all();
			map.put(Constant.JSON, JsonUtils.object2json(budget));
			return Constant.JSON;
		}
		return "admin/budget-list";
	}

	@MenuTag(code = "constant-budget-save", name = "添加预算", sequence = 1, type = 2, parentCode = "constant-budget-index")
	@RequestMapping(value = "/budget-save")
	@ResponseBody
	public JsonDTO budgetSave(Budget budget) {
		JsonDTO json = new JsonDTO();
		try {
			budgetService.saveOrUpdate(budget);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-budget-edit", name = "修改预算", sequence = 2, type = 2, parentCode = "constant-budget-index")
	@RequestMapping(value = "/budget-edit")
	@ResponseBody
	public JsonDTO budgetEdit(Budget budget) {
		JsonDTO json = new JsonDTO();
		try {
			budgetService.saveOrUpdate(budget);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-budget-delete", name = "删除预算", sequence = 2, type = 2, parentCode = "constant-budget-index")
	@RequestMapping(value = "{budgetId}/age-delete")
	@ResponseBody
	public JsonDTO budgetDelete(@PathVariable("budgetId") Integer budgetId) {
		JsonDTO json = new JsonDTO();
		try {
			budgetService.delete(budgetId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}

	/** 预算结束 */

	/** 房型开始 */
	@Resource
	private HouseStyleService styleService;
	
	@Resource
	private CompanyTypeService companyTypeService;

	@MenuTag(code = "constant-style-index", name = "房型管理", sequence = 2, type = 1)
	@RequestMapping(value = "/style-index")
	public String styleIndex(String operator, Map<String, Object> map,Integer styleId) {
		List<CompanyType> types=companyTypeService.all();
		if (Constant.LIST.equals(operator)) {
			List<Map<String,Object>> defaultSet = styleService.getDefaultPrice(types);
			map.put(Constant.JSON, JsonUtils.object2json(defaultSet));
			return Constant.JSON;
		}else if("styleId".equals(operator)){
			List<Map<String,Object>> companySet=styleService.getCompanyPriceByStyle(types, styleId);
			map.put(Constant.JSON, JsonUtils.object2json(companySet));
			return Constant.JSON;
		}
		map.put("types", types);
		return "admin/house-style-list";
	}

	@MenuTag(code = "constant-style-save", name = "添加房型", sequence = 1, type = 2, parentCode = "constant-style-index")
	@RequestMapping(value = "/style-save")
	@ResponseBody
	public JsonDTO styleSave(
			@RequestBody HouseStyle houseStyle) {
		JsonDTO json = new JsonDTO();
		try {
			styleService.saveOrUpdate(houseStyle);
			json.setStatus(1).setMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("保存过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-style-edit", name = "修改房型及价格", sequence = 2, type = 2, parentCode = "constant-style-index")
	@RequestMapping(value = "/style-edit")
	@ResponseBody
	public JsonDTO styleEdit(@RequestBody HouseStyle houseStyle) {
		JsonDTO json = new JsonDTO();
		try {
			styleService.saveOrUpdate(houseStyle);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}
	@MenuTag(code = "constant-company-style-edit", name = "公司业务房型价格", sequence = 3, type = 2, parentCode = "constant-style-index")
	@RequestMapping(value = "/company-style-edit")
	@ResponseBody
	public JsonDTO companyStyleEdit(@RequestBody HouseStyle houseStyle) {
		JsonDTO json = new JsonDTO();
		try {
			styleService.saveOrUpdate(houseStyle);
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("修改过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "constant-style-delete", name = "删除房型", sequence = 3, type = 2, parentCode = "constant-style-index")
	@RequestMapping(value = "{styleId}/style-delete")
	@ResponseBody
	public JsonDTO styleDelete(@PathVariable("styleId") Integer styleId) {
		JsonDTO json = new JsonDTO();
		try {
			styleService.delete(styleId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}
	
	@MenuTag(code = "constant-company-style-edit", name = "修改公司业务房型价格", sequence = 3, type = 2, 
			parentCode = "constant-company-style-edit")
	@RequestMapping(value = "constant-company-style-edit")
	@ResponseBody
	public JsonDTO styleCompanyEdit(@PathVariable("styleId") Integer styleId) {
		JsonDTO json = new JsonDTO();
		try {
			styleService.delete(styleId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}

	/** 房型结束 */

	/** 短信开始 */
	@Resource
	private MessageTemplateService messageTemplateService;

	@MenuTag(code = "message-template-index", name = "短信模板管理", sequence = 3, type = 1)
	@RequestMapping(value = "/message-template-index")
	public String messageTemplateIndex(String operator, Map<String, Object> map) {
		if (Constant.LIST.equals(operator)) {
			List<MessageTemplateDTO> budget = messageTemplateService.all();
			map.put(Constant.JSON, JsonUtils.object2json(budget));
			return Constant.JSON;
		}
		return "admin/message_template_index";
	}

	@MenuTag(code = "message-template-save", name = "添加短信模板", sequence = 1, type = 2, parentCode = "message-template-index")
	@RequestMapping(value = "/message-template-save")
	@ResponseBody
	public JsonDTO messageTemplateSave(MessageTemplate messageTemplate, HttpServletRequest request) {
		JsonDTO result = new JsonDTO();
		messageTemplate.setCreateDate(new Date());
		messageTemplate.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		messageTemplate.setStatus(1);
		messageTemplate.setIsDelete(0);
		try {
			messageTemplateService.saveOrUpdate(messageTemplate);
			result.setStatus(1).setMessage("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("添加过程中，后台出现异常，请联系管理员");
		}

		return result;
	}

	@MenuTag(code = "message-template-update", name = "修改短信模板", sequence = 1, type = 3, parentCode = "message-template-index")
	@RequestMapping(value = "/message-template-update")
	@ResponseBody
	public JsonDTO messageTemplateUpdate(MessageTemplate messageTemplate, HttpServletRequest request) {
		JsonDTO result = new JsonDTO();
		messageTemplate.setCreateDate(new Date());
		messageTemplate.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		messageTemplate.setStatus(1);
		messageTemplate.setIsDelete(0);
		try {
			messageTemplateService.saveOrUpdate(messageTemplate);
			result.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(0).setMessage("修改过程中，后台出现异常，请联系管理员");
		}

		return result;
	}

	@MenuTag(code = "message-template-delete", name = "删除短信模版", sequence = 3, type = 2, parentCode = "message-template-index")
	@RequestMapping(value = "message/{templateId}/delete")
	@ResponseBody
	public JsonDTO messageTemplateDelete(@PathVariable("templateId") Integer templateId) {
		JsonDTO json = new JsonDTO();
		try {
			messageTemplateService.delete(templateId);
			json.setStatus(1).setMessage("删除成功！");
		} catch (Exception e) {
			json.setStatus(0).setMessage("删除过程中，系统出现异常");
		}
		return json;
	}

	@MenuTag(code = "message-template-status", name = "短信状态管理", sequence = 4, type = 2, parentCode = "message-template-index")
	@RequestMapping(value = "message/{templateId}/status")
	@ResponseBody
	public JsonDTO messageTemplateStatus(@PathVariable("templateId") Integer templateId) {
		JsonDTO json = new JsonDTO();
		MessageTemplateDTO temp = messageTemplateService.get(templateId);
		String message = null;
		Integer status = null;
		if (temp != null) {
			if (temp.getStatus() == 0) {
				message = "激活成功！";
				status = 1;
			} else {
				message = "冻结成功";
				status = 0;
			}
			MessageTemplate param = new MessageTemplate();
			param.setStatus(status);
			param.setTemplateId(templateId);
			try {
				messageTemplateService.saveOrUpdate(param);
				json.setStatus(1).setMessage(message);
			} catch (Exception e) {
				json.setStatus(0).setMessage("操作过程中，系统出现异常");
			}
		} else {
			json.setStatus(0).setMessage("该短信不存在");
		}
		return json;
	}
}
