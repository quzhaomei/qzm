package com.sf.qzm.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.bean.menu.AutoMenu;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

public class MenuController extends BaseController {
	@Resource
	private AutoMenuService menuManagerService;

	// 负责跳转菜单页面
	@RequestMapping(value = "/index")
	public String index(Model model, String operator) {
		if (Constant.LIST.equals(operator)) {// 获取所有
			List<AutoMenu> menus = menuManagerService.getAllMenu();// 获取所有的menu
			model.addAttribute("json", JsonUtils.object2json(menus));
			return Constant.JSON;
		}
		return "admin/menuManager";
	}

	// 负责所有的查询逻辑以及ajax
	@RequestMapping(value = "/list")
	@ResponseBody
	public JsonDTO list(Integer menuId, String operator) {
		JsonDTO json = new JsonDTO();
		if (Constant.FIND_BY_ID.equals(operator)) {// 获取当个
			json.setStatus(1).setData(menuManagerService.getById(menuId));
		} else {
			json.setStatus(0).setMessage("参数异常");
		}
		return json;
	}


}
