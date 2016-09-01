package com.sf.qzm.controller.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.sf.qzm.bean.biz.Designer;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.biz.CompanyDTO;
import com.sf.qzm.dto.biz.DesignerDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.CompanyService;
import com.sf.qzm.service.CompanyTypeService;
import com.sf.qzm.service.CompanyZoneService;
import com.sf.qzm.service.DesignerService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("company")
@MenuTag(code = "company", name = "公司设计师管理", sequence = 1, type = 0, icon = "icon-business_center")
public class CompanyController extends BaseController {
	@Resource
	private CompanyService companyService;
	@Resource
	private CompanyTypeService companyTypeService;

	@Resource
	private AdminUserService adminUserService;

	@MenuTag(code = "company-company", name = "公司信息管理", sequence = 1, type = 1)
	@RequestMapping(value = "/company-company")
	public String company(String operator, Map<String, Object> map) {
		if (Constant.LIST.equals(operator)) {
			List<CompanyDTO> list = companyService.all();
			map.put(Constant.JSON, JsonUtils.object2json(list));
			return Constant.JSON;
		} else if ("typeList".equals(operator)) {
			List<CompanyType> list = companyTypeService.all();
			map.put(Constant.JSON, JsonUtils.object2json(list));
			return Constant.JSON;
		}
		// 查找所有店铺类型
		List<CompanyType> types = companyTypeService.all();
		map.put("types", types);

		// 查找所有店主
		List<AdminUserDTO> keepers = adminUserService.getByPower("company-my-company");
		map.put("keepers", keepers);

		return "admin/company-list";
	}

	@MenuTag(code = "company-company-save", name = "添加公司店铺", sequence = 3, type = 2, parentCode = "company-company")
	@RequestMapping(value = "/company-save")
	@ResponseBody
	public JsonDTO companySave(@RequestBody Company company, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO json = new JsonDTO();

		company.setCreateDate(new Date());
		company.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		company.setAccept(1);
		company.setStatus(0);
		try {
			companyService.saveOrUpdate(company);
			json.setStatus(1).setMessage("添加店铺成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("添加店铺失败");
		}
		return json;
	}

	@MenuTag(code = "company-company-update", name = "更新公司店铺", sequence = 4, type = 2, parentCode = "company-company")
	@RequestMapping(value = "/company-update")
	@ResponseBody
	public JsonDTO companyUpdate(@RequestBody Company company, Map<String, Object> map, HttpServletRequest request) {
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

	@Resource
	private CompanyZoneService companyZoneService;

	// 查询店铺接单区域
	@RequestMapping(value = "/companyZone")
	@ResponseBody
	public List<Map<String, Object>> companyZone(Integer companyId) {
		List<Map<String, Object>> zones = new ArrayList<Map<String, Object>>();
		List<Zone> roots = companyZoneService.root(companyId);
		for (Zone temp : roots) {
			Map<String, Object> tempRoot = new HashMap<String, Object>();
			tempRoot.put("zoneId", temp.getZoneId());
			tempRoot.put("name", temp.getName());
			tempRoot.put("tempId", temp.getTempId());
			tempRoot.put("children", initChildren(temp.getZoneId(), companyId));
			zones.add(tempRoot);
		}
		return zones;
	}

	private List<Map<String, Object>> initChildren(Integer parentId, Integer companyId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Zone> children = companyZoneService.list(parentId, companyId);
		if (children != null && children.size() > 0) {
			for (Zone temp : children) {
				Map<String, Object> tempRoot = new HashMap<String, Object>();
				tempRoot.put("zoneId", temp.getZoneId());
				tempRoot.put("name", temp.getName());
				tempRoot.put("tempId", temp.getTempId());
				tempRoot.put("children", initChildren(temp.getZoneId(), companyId));
				result.add(tempRoot);
			}
		}
		return result;
	}

	/**
	 * 管理公司业务类别
	 * 
	 * @param channel
	 * @param request
	 */
	@MenuTag(code = "company-company-type-save", name = "添加业务类别", sequence = 5, type = 2, parentCode = "company-company")
	@RequestMapping(value = "/company-type-save")
	@ResponseBody
	public JsonDTO companyTypeSave(CompanyType companyType, Map<String, Object> map) {
		JsonDTO json = new JsonDTO();
		try {
			companyTypeService.saveOrUpdate(companyType);
			json.setStatus(1).setMessage("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("添加失败！");
		}
		return json;
	}

	/**
	 * 管理公司业务类别
	 * 
	 * @param channel
	 * @param request
	 */
	@MenuTag(code = "company-company-type-update", name = "修改业务类别", sequence = 5, type = 2, parentCode = "company-company")
	@RequestMapping(value = "/company-type-update")
	@ResponseBody
	public JsonDTO companyTypeUpdate(CompanyType companyType, Map<String, Object> map) {
		JsonDTO json = new JsonDTO();
		try {
			companyTypeService.saveOrUpdate(companyType);
			companyService.clear();
			json.setStatus(1).setMessage("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("修改失败！");
		}
		return json;
	}

	@Resource
	private DesignerService designerService;

	@MenuTag(code = "company-designer", name = "设计师信息管理", sequence = 2, type = 1)
	@RequestMapping(value = "/designer")
	public String designerIndex(String operator, Map<String, Object> map) {
		if (Constant.LIST.equals(operator)) {
			List<DesignerDTO> list = designerService.all();
			map.put(Constant.JSON, JsonUtils.object2json(list));
			return Constant.JSON;
		}
		// 查找所有店主
		List<AdminUserDTO> keepers = adminUserService.getByPower("company-my-company");
		map.put("keepers", keepers);
		return "admin/designer-list";
	}

	@MenuTag(code = "designer-save", name = "添加设计师", sequence = 2, type = 2, parentCode = "company-designer")
	@RequestMapping(value = "/designerSave")
	@ResponseBody
	public JsonDTO designerSave(Designer designer, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO json = new JsonDTO();
		designer.setCreateDate(new Date());
		designer.setCreateUserId(getLoginAdminUser(request).getAdminUserId());
		designer.setAccept(1);
		try {
			designerService.saveOrUpdate(designer);
			json.setStatus(1).setMessage("添加设计师成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("添加设计师失败");
		}
		return json;
	}

	@MenuTag(code = "designer-update", name = "更新设计师", sequence = 4, type = 2, parentCode = "company-designer")
	@RequestMapping(value = "/designer-update")
	@ResponseBody
	public JsonDTO designerUpdate(@RequestBody Designer designer, Map<String, Object> map, HttpServletRequest request) {
		JsonDTO json = new JsonDTO();
		try {
			designerService.saveOrUpdate(designer);
			json.setStatus(1).setMessage("修改设计师成功！");
		} catch (Exception e) {
			e.printStackTrace();
			json.setStatus(0).setMessage("修改设计师失败");
		}
		return json;
	}

	// 查询设计师接单区域
	@RequestMapping(value = "/designZone")
	@ResponseBody
	public List<Map<String, Object>> designZone(Integer designerId) {
		List<Map<String, Object>> zones = new ArrayList<Map<String, Object>>();
		List<Zone> roots = designerService.rootByDesigner(designerId);
		for (Zone temp : roots) {
			Map<String, Object> tempRoot = new HashMap<String, Object>();
			tempRoot.put("zoneId", temp.getZoneId());
			tempRoot.put("name", temp.getName());
			tempRoot.put("tempId", temp.getTempId());
			tempRoot.put("children", initZoneChildren(temp.getZoneId(), designerId));
			zones.add(tempRoot);
		}
		return zones;
	}

	private List<Map<String, Object>> initZoneChildren(Integer parentId, Integer designerId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Zone> children = designerService.listByDesigner(parentId, designerId);
		if (children != null && children.size() > 0) {
			for (Zone temp : children) {
				Map<String, Object> tempRoot = new HashMap<String, Object>();
				tempRoot.put("zoneId", temp.getZoneId());
				tempRoot.put("name", temp.getName());
				tempRoot.put("tempId", temp.getTempId());
				tempRoot.put("children", initZoneChildren(temp.getZoneId(), designerId));
				result.add(tempRoot);
			}
			
		}
		return result;
	}

	/**
	 * 我的店铺
	 */
	@MenuTag(code = "company-my-company", name = "我的店铺", sequence = 4, type = 1)
	@RequestMapping(value = "/company-my-company")
	public String myCompany(String operator, Map<String, Object> map) {
		return "admin/company-list";
	}

}
