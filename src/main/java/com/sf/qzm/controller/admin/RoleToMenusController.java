package com.sf.qzm.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.annotation.ClassLimit;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.MenuManagerDTO;
import com.sf.qzm.dto.admin.RoleManagerDTO;
import com.sf.qzm.service.RoleManagerService;
import com.sf.qzm.service.RoleToMenusService;
import com.sf.qzm.util.other.Constant;

/**
 * 角色权限控制
 * 
 * @author qzm
 * @since 2015-8-31
 */
@Controller
@RequestMapping(value = "power")
@ClassLimit(uri = true)
public class RoleToMenusController extends BaseController {

	@Resource
	private RoleManagerService roleManagerService;

	@Resource
	private RoleToMenusService roleToMenusService;

	// 跳转的页面
	@RequestMapping(value = "/index")
	public String index(Model model) {
		// 查询所有的role
		List<RoleManagerDTO> allRoles = roleManagerService.getAllRole();
		model.addAttribute("allRoles", allRoles);
		return "admin/power";
	}

	// 负责菜单的查询
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonDTO list(Integer roleId, String operator, Model model) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 加载单个菜单
			RoleManagerDTO role = roleManagerService.getById(roleId);
			if (role != null) {
				List<MenuManagerDTO> menus = roleToMenusService.getRoleMenusByRoleId(roleId);
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("role", role);
				result.put("menus", menus);
				json.setStatus(1).setData(result);
				return json;
			}

		}
		json.setStatus(0).setMessage("参数异常");
		return json;
	}

	// 登陆后跳转的页面
	@RequestMapping(value = "/saveOrUpdate")
	@ResponseBody
	public JsonDTO saveOrUpdate(String operator, Integer roleId, 
			@RequestParam(value="menuIds[]",required=false) String[] menuIds,
			@RequestParam(value="tempIds[]",required=false) String[] tempIds, Model model) {
		JsonDTO json = new JsonDTO();
		if (Constant.EDIT.equals(operator) && roleId != null) {
			RoleManagerDTO role = roleManagerService.getById(roleId);
			if (role != null) {// 检测role存在与否
				try {
					roleToMenusService.batchUpdateRoleMenus(menuIds, tempIds, roleId);
					json.setStatus(1).setMessage("更新成功");
				} catch (Exception e) {
					e.printStackTrace();
					json.setStatus(0).setMessage("更新失败:" + e.getMessage());
				}
				return json;
			}
		}
		json.setStatus(0).setMessage("参数错误");
		return json;
	}
}
