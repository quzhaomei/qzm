package com.sf.qzm.controller.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.biz.Company;
import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.CompanyService;
import com.sf.qzm.service.CompanyTypeService;
import com.sf.qzm.service.CustomerHouseService;
import com.sf.qzm.service.OrderService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("company")
@MenuTag(code = "myCompany", name = "我的店铺", sequence = 5, type = 0,icon="icon-shop")
public class CompanyMyController extends BaseController {
	
	@Resource
	private CompanyTypeService companyTypeService;
	@Resource
	private CompanyService companyService;
	@Resource
	private OrderService orderService;
	@Resource
	private CustomerHouseService customerHouseService;
	@Resource
	private ZoneService zoneService;
	@Resource
	private AdminUserService adminUserService;
	/**
	 * 我的店铺
	 */
	@MenuTag(code = "company-my-company", name = "我的店铺", sequence = 4, type = 1)
	@RequestMapping(value = "/company-my-company")
	public String myCompany(String operator, Map<String, Object> map,HttpServletRequest request) {
		if (Constant.LIST.equals(operator)) {
			List<CompanyDTO> list = companyService.list(getLoginAdminUser(request).getAdminUserId());
			map.put(Constant.JSON, JsonUtils.object2json(list));
			return Constant.JSON;
		} 
		// 查找所有店铺类型
		List<CompanyType> types = companyTypeService.all();
		map.put("types", types);

		// 查找所有店主
		List<AdminUserDTO> keepers = adminUserService.getByPower("company-my-company");
		map.put("keepers", keepers);

		return "admin/my-company-list";
	}
	
	@MenuTag(code = "my-company-company-update", name = "更新自己店铺", sequence = 4, type = 2, parentCode = "company-my-company")
	@RequestMapping(value = "/my-company-update")
	@ResponseBody
	public JsonDTO companyUpdateMy(@RequestBody Company company, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO json = new JsonDTO();
		try {
			companyService.saveOrUpdate(company);
			json.setStatus(1).setMessage("修改店铺成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("修改店铺失败");
		}
		return json;
	}
	
}
